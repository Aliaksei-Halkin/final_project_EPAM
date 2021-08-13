package com.epam.flyingdutchman.model.connection;

import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * The type Connection pool.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public enum ConnectionPool {
    INSTANCE;
    private final Logger logger = LogManager.getLogger();

    private final int DEFAULT_POOL_SIZE = 8;
    private final BlockingQueue<ProxyConnection> freeConnection;
    private final BlockingQueue<ProxyConnection> givenConnections;

    /**
     * Initialize connection pool
     */
    ConnectionPool() {
        final String DRIVER_DB = ConfigurationManager.getProperty("db.driver");
        final String URL_DB = ConfigurationManager.getProperty("db.url");
        final String USER_DB = ConfigurationManager.getProperty("db.user");
        final String PASSWORD_DB = ConfigurationManager.getProperty("db.password");
        freeConnection = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        try {
            Class.forName(DRIVER_DB);
            logger.info("JDBC driver loaded");
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB);
                freeConnection.put(new ProxyConnection(connection));
                logger.info("DB connection created");
            }
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            logger.fatal("Error while creating connection pool", e);
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenConnections.put(connection);
        } catch (InterruptedException e) {
            logger.fatal("InterruptedException in method getConnection", e);
        }
        return connection;
    }

    /**
     * Release connection.
     *
     * @param connection the connection
     */
    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection && givenConnections.remove(connection)) {
            try {
                freeConnection.put((ProxyConnection) connection);
            } catch (InterruptedException e) {
                logger.fatal("InterruptedException in method getConnection", e);
            }
        } else {
            logger.warn("Not original connection returned to the pool");
        }
    }

    /**
     * Destroy pool.
     */
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnection.take().reallyClose();
                logger.info("DB connection closed");
            } catch (InterruptedException | SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
                logger.info("JDBC driver deregistered");
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }
}
