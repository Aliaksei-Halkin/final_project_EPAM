package com.epam.flyingdutchman.model.connection;

import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class ConnectionPool {
    private static final int POOL_SIZE = 10;
    private static final ConnectionPool pool = new ConnectionPool();
    private final Logger logger = LogManager.getLogger();
    private final BlockingDeque<ProxyConnection> freeConnection;
    private final Queue<ProxyConnection> givenConnections;


    ConnectionPool() {
        freeConnection = new LinkedBlockingDeque<>(POOL_SIZE);
        givenConnections = new ArrayDeque<>();
        String driverDB = ConfigurationManager.getProperty("db.driver");
        String urlDB = ConfigurationManager.getProperty("db.url");
        String userDB = ConfigurationManager.getProperty("db.user");
        String passwordDB = ConfigurationManager.getProperty("db.password");
        try {
            Class.forName(driverDB);
            logger.info("JDBC driver loaded");
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(urlDB, userDB, passwordDB);
                freeConnection.offer(new ProxyConnection(connection));
                logger.info("DB connection created");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
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

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenConnections.offer(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection && givenConnections.remove(connection)) {
            freeConnection.offer((ProxyConnection) connection);
        } else {
            logger.warn("Not original connection returned to the pool");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
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
