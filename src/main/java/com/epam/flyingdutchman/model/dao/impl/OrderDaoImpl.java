package com.epam.flyingdutchman.model.dao.impl;

import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.entity.Status;
import com.epam.flyingdutchman.exception.DaoException;
import com.epam.flyingdutchman.model.connection.ConnectionPool;
import com.epam.flyingdutchman.model.dao.OrderDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.epam.flyingdutchman.util.constants.DatabaseColumn.*;

public class OrderDaoImpl implements OrderDao {
    private static final String SELECT_ORDERS_BY_USER = "SELECT * FROM orders WHERE username = ? LIMIT ?,?";
    private static final int SELECT_BY_USER_USERNAME_INDEX = 1;
    private static final int SELECT_BY_USER_LIMIT_CURRENT_INDEX = 2;
    private static final int SELECT_BY_USER_LIMIT_ON_PAGE_INDEX = 3;
    private static final String SELECT_ORDERS_DETAILS = "SELECT * FROM orders_details "
            + "LEFT JOIN products ON orders_details.product_id = products.product_id "
            + "WHERE orders_details.order_id = ?";
    private static final String INSERT_ORDERS_DETAILS = "INSERT INTO orders_details VALUES (?, ?, ?)";
    private static final int INSERT_ORDERS_DETAILS_ID_COLUMN = 1;
    private static final int INSERT_ORDERS_DETAILS_PRODUCT_ID_COLUMN = 2;
    private static final int INSERT_ORDERS_DETAILS_NUMBER_OF_PRODUCTS = 3;
    private static final String INSERT_ORDER = "INSERT INTO orders (username,  order_cost) VALUES (?, ?)";
    private static final int INSERT_ORDERS_USERNAME_COLUMN = 1;
    private static final int INSERT_ORDERS_DATE_COLUMN = 2;
    private static final int INSERT_ORDERS_COST_COLUMN = 3;
    private static final int INSERT_ORDERS_COST_COLUMN_NEW_ORDER = 2;
    private static final int INSERT_ORDERS_STATUS_CONFIRM_COLUMN = 4;
    private static final String SELECT_ALL_ORDERS_WITHOUT_STATUS_CLOSE =
            "SELECT * FROM orders WHERE status <> 'CLOSED'  ORDER BY order_date DESC  LIMIT ?, ?";
    private static final int SELECT_ALL_LIMIT_CURRENT_INDEX = 1;
    private static final int SELECT_ALL_LIMIT_ON_PAGE_INDEX = 2;
    private static final int UPDATE_ORDER_ID_COLUMN = 5;
    private static final String COUNT_ORDERS_BY_USER = "SELECT COUNT(*) FROM orders WHERE username = ?";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?";
    private static final String UPDATE_ORDER = "UPDATE orders SET username = ?, order_date = ?, order_cost = ?, "
            + "status = ? WHERE order_id = ?";
    private static final String COUNT_ORDERS = "SELECT COUNT(*) FROM orders WHERE status <> 'CLOSED' ";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ?";
    private static final String DELETE_ORDERS_DETAILS = "DELETE FROM orders_details WHERE order_id = ?";
    private final int INVALID_VALUE = -1;
    private final Logger logger = LogManager.getLogger();
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static OrderDaoImpl instance;

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        while (instance == null) {
            if (isInitialized.compareAndSet(false, true)) {
                instance = new OrderDaoImpl();
            }
        }
        return instance;
    }

    @Override
    public List<Order> findOrdersByUser(String username, int currentIndex, int itemsOnPage) throws DaoException {
        List<Order> orders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_USER)) {
            preparedStatement.setString(SELECT_BY_USER_USERNAME_INDEX, username);
            preparedStatement.setInt(SELECT_BY_USER_LIMIT_CURRENT_INDEX, currentIndex);
            preparedStatement.setInt(SELECT_BY_USER_LIMIT_ON_PAGE_INDEX, itemsOnPage);
            ResultSet ordersSet = preparedStatement.executeQuery();
            while (ordersSet.next()) {
                orders.add(createInstanceOfOrder(connection, ordersSet));
            }
            ordersSet.close();
        } catch (SQLException throwables) {
            logger.error("Error get Orders By User ", throwables);
            throw new DaoException(throwables.getSQLState(), throwables);//todo chrck method getSQLState
        }
        return orders;
    }

    private Order createInstanceOfOrder(Connection connection, ResultSet ordersSet) throws DaoException {
        Map<Product, Long> products;
        Order order;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_DETAILS)) {
            int orderId = ordersSet.getInt(ORDERS_ORDER_ID);
            statement.setInt(1, orderId);
            ResultSet productsSet = statement.executeQuery();
            products = createMapOfProducts(productsSet);
            String username = ordersSet.getString(ORDERS_USERNAME);
            LocalDateTime orderDate = ordersSet.getTimestamp(ORDERS_ORDER_DATE).toLocalDateTime();
            BigDecimal cost = ordersSet.getBigDecimal(ORDERS_ORDER_COST);
            Status status = Status.valueOf(ordersSet.getString(ORDERS_STATUS).toUpperCase());
            order = new Order(orderId, username, orderDate, cost, status, products);
            productsSet.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error creating a row in the table Order", e);
        }
        return order;
    }

    private Map<Product, Long> createMapOfProducts(ResultSet productsSet) throws DaoException {
        Map<Product, Long> productsMap = new HashMap<>();

        try {
            while (productsSet.next()) {
                productsMap.put(createInstanceOfProduct(productsSet),
                        productsSet.getLong(ORDERS_DETAILS_NUMBER_OF_PRODUCTS));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error creating map of products from ResultSet", e);
        }
        return productsMap;
    }

    private Product createInstanceOfProduct(ResultSet productsSet) throws DaoException {
        Product product = new Product();
        try {
            product.setId(productsSet.getInt(PRODUCTS_PRODUCT_ID));
            product.setName(productsSet.getString(PRODUCTS_PRODUCT_NAME));
            product.setProductImgPath(productsSet.getString(PRODUCTS_IMAGE_PATH));
            product.setCost(productsSet.getBigDecimal(PRODUCTS_COST));
            product.setDescription(productsSet.getString(PRODUCTS_DESCRIPTION));
            product.setActive(productsSet.getBoolean(PRODUCTS_ACTIVE));
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error creating a product ", e);
        }
        return product;
    }

    @Override
    public int countOrdersWithoutStatusClose() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet counter = statement.executeQuery(COUNT_ORDERS)) {
            if (counter.next()) {
                return counter.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error counting allOrders from ResultSet", e);
            throw new DaoException("Error counting allOrders from ResultSet", e);
        }
        return INVALID_VALUE;
    }

    @Override
    public int countOrdersOfUser(String username) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ORDERS_BY_USER)) {
            preparedStatement.setString(1, username);
            ResultSet counterSet = preparedStatement.executeQuery();
            if (counterSet.next()) {
                return counterSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error counting orders of user from ResultSet", e);
            throw new DaoException("Error counting orders of user from ResultSet", e);
        }
        return INVALID_VALUE;
    }

    @Override
    public int createOrder(Order order) throws DaoException, SQLException {
        int orderId;
        Connection connection = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.INSTANCE;
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            orderId = insertInTheTableOrders(connection, order);
            insertInTheTableOrdersDetails(connection, order, orderId);
            connection.commit();
        } catch (SQLException throwables) {
            connection.rollback();
            logger.error("Error creating new order  ", throwables);
            throw new DaoException("Error creating new order  ", throwables);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException throwables) {
                    logger.error("Error while the connection in the method createOrder of OrderDaoImpl " +
                            "didn't close or didn't have autocommit", throwables);
                }
            }
        }
        return orderId;
    }

    private void insertInTheTableOrdersDetails(Connection connection, Order order, int orderId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDERS_DETAILS)) {
            for (Map.Entry<Product, Long> product : order.getListOfProducts().entrySet()) {
                statement.setInt(INSERT_ORDERS_DETAILS_ID_COLUMN, orderId);
                statement.setLong(INSERT_ORDERS_DETAILS_PRODUCT_ID_COLUMN, product.getKey().getId());
                statement.setLong(INSERT_ORDERS_DETAILS_NUMBER_OF_PRODUCTS, product.getValue());
                statement.executeUpdate();
            }
        } catch (SQLException throwables) {
            logger.error("Error inserting rows in the table Order_details", throwables);
            throw new DaoException("Error inserting rows in the table Order_details", throwables);
        }
    }

    private int insertInTheTableOrders(Connection connection, Order order) throws DaoException, SQLException {
        int orderId;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(INSERT_ORDERS_USERNAME_COLUMN, order.getUserName());
            statement.setBigDecimal(INSERT_ORDERS_COST_COLUMN_NEW_ORDER, order.getOrderCost());
            statement.executeUpdate();
            orderId = getKey(statement);
        } catch (SQLException throwables) {
            connection.rollback();
            logger.error("Error inserting row in the table Order", throwables);
            throw new DaoException("Error inserting row in the table Order", throwables);
        }
        return orderId;
    }

    private int getKey(PreparedStatement preparedStatement) throws DaoException {
        int orderId = INVALID_VALUE;
        try (ResultSet key = preparedStatement.getGeneratedKeys()) {
            if (key.next()) {
                orderId = key.getInt(1);
            }
        } catch (SQLException throwables) {
            logger.error("Error getting key after inserting row in a table", throwables);
            throw new DaoException("Error getting key after inserting row in a table", throwables);
        }
        return orderId;
    }

    @Override
    public boolean deleteOrder(int orderId) throws DaoException, SQLException {
        Connection connection = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.INSTANCE;
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            deleteInTableOrdersDetails(connection, orderId);
            deleteRowInTableOrders(connection, orderId);
            connection.commit();
            return true;
        } catch (SQLException throwables) {
            connection.rollback();
            logger.error("Error deleting a order " + orderId, throwables);
            throw new DaoException("Error deleting a order", throwables);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException throwables) {
                    logger.error("Error while the connection in the method deleteOrder of OrderDaoImpl " +
                            "didn't close or didn't have autocommit", throwables);
                }
            }
        }
    }

    private void deleteRowInTableOrders(Connection connection, int orderId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ORDER)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Error delete Row In Table Orders", throwables);
            throw new DaoException("Error delete Row In Table Orders", throwables);

        }
    }

    private void deleteInTableOrdersDetails(Connection connection, int orderId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ORDERS_DETAILS)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Error deleting in the order_details", throwables);
            throw new DaoException("Error deleting in the order_details", throwables);

        }
    }

    @Override
    public List<Order> findAllOrdersWithoutStatusClose(int currentIndex, int itemsOnPage) throws DaoException {
        List<Order> orders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS_WITHOUT_STATUS_CLOSE)) {
            statement.setInt(SELECT_ALL_LIMIT_CURRENT_INDEX, currentIndex);
            statement.setInt(SELECT_ALL_LIMIT_ON_PAGE_INDEX, itemsOnPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(createInstanceOfOrder(connection, resultSet));
            }
        } catch (SQLException throwables) {
            logger.error("Error getting all orders", throwables);
            throw new DaoException("Error getting all orders", throwables);

        }
        return orders;
    }

    @Override
    public Order getOrderById(int orderId) throws DaoException {
        Order order = null;
        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = createInstanceOfOrder(connection, resultSet);
            }
        } catch (SQLException throwables) {
            logger.error("Error getting order by id", throwables);
            throw new DaoException("Error getting order by id", throwables);
        }
        return order;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {
            statement.setString(INSERT_ORDERS_USERNAME_COLUMN, order.getUserName());
            statement.setTimestamp(INSERT_ORDERS_DATE_COLUMN, Timestamp.valueOf(order.getOrderDateTime()));
            statement.setBigDecimal(INSERT_ORDERS_COST_COLUMN, order.getOrderCost());
            statement.setString(INSERT_ORDERS_STATUS_CONFIRM_COLUMN, order.getStatus().toString());
            statement.setInt(UPDATE_ORDER_ID_COLUMN, order.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Error updating order by id", throwables);
            throw new DaoException("Error updating order by id", throwables);
        }
        return true;//todo check
    }
}



