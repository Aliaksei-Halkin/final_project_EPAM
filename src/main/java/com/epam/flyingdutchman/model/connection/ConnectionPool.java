package com.epam.flyingdutchman.model.connection;

import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The type Connection pool.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class ConnectionPool {
    private final Logger logger = LogManager.getLogger();
    private static final String DRIVER_DB = ConfigurationManager.getProperty("db.driver");
    private static final String URL_DB = ConfigurationManager.getProperty("db.url");
    private static final String USER_DB = ConfigurationManager.getProperty("db.user");
    private static final String PASSWORD_DB = ConfigurationManager.getProperty("db.password");
    private static final int DEFAULT_POOL_SIZE = 8;
    private static final ConnectionPool pool = new ConnectionPool();
    private final BlockingQueue<ProxyConnection> freeConnection;
    private final BlockingQueue<ProxyConnection> givenConnections;

    /**
     * Initialize connection pool
     */
    private ConnectionPool() {
        freeConnection = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);
        givenConnections = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);//todo blockingQueue
        try {
            Class.forName(DRIVER_DB);
            logger.info("JDBC driver loaded");
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB);
                freeConnection.offer(new ProxyConnection(connection));
                logger.info("DB connection created");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.fatal("Error while creating connection pool", e);
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConnectionPool getInstance() {
        return pool;
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
            givenConnections.offer(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            freeConnection.offer((ProxyConnection) connection);
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
