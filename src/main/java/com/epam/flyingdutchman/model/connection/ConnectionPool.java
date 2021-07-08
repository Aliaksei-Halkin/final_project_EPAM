package com.epam.flyingdutchman.model.connection;

import com.epam.flyingdutchman.util.resources.ConfigurationManager;

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
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(urlDB, userDB, passwordDB);
                freeConnection.offer(new ProxyConnection(connection));
            }
        } catch (SQLException | ClassNotFoundException e) {
            // TODO: 6/19/2021 will add a logger
            e.printStackTrace();

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

        } catch (InterruptedException e) {
            e.printStackTrace();
            // TODO: 6/19/2021 will add a logger
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection && givenConnections.remove(connection)) {
            freeConnection.offer((ProxyConnection) connection);
        } else {
            // TODO: 6/19/2021 will add logger
        }
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnection.take().reallyClose();
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();             // TODO: 6/19/2021 will add logger
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);

            } catch (SQLException e) {

            }
        });
    }
}
