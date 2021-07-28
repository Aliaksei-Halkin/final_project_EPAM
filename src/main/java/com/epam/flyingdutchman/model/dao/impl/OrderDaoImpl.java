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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.flyingdutchman.util.constants.DatabaseColumn.*;
import static com.epam.flyingdutchman.util.constants.DatabaseQuery.*;

public class OrderDaoImpl implements OrderDao {
    private static final int SELECT_BY_USER_USERNAME_INDEX = 1;
    private static final int SELECT_BY_USER_LIMIT_CURRENT_INDEX = 2;
    private static final int SELECT_BY_USER_LIMIT_ON_PAGE_INDEX = 3;
    private static final int INSERT_ORDERS_DETAILS_ID_COLUMN = 1;
    private static final int INSERT_ORDERS_DETAILS_PRODUCT_ID_COLUMN = 2;
    private static final int INSERT_ORDERS_DETAILS_NUMBER_OF_PRODUCTS = 3;
    private static final int INSERT_ORDERS_USERNAME_COLUMN = 1;
    private static final int INSERT_ORDERS_DATE_COLUMN = 2;
    private static final int INSERT_ORDERS_COST_COLUMN = 3;
    private static final int INSERT_ORDERS_STATUS_CONFIRM_COLUMN = 4;
    private static final int SELECT_ALL_LIMIT_CURRENT_INDEX = 1;
    private static final int SELECT_ALL_LIMIT_ON_PAGE_INDEX = 2;
    private static final int UPDATE_ORDER_ID_COLUMN = 5;
    private static final int INVALID_ID = -1;
    private static final int INVALID_COUNT = -1;
    private static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final OrderDaoImpl INSTANCE = new OrderDaoImpl();
    private final Logger logger = LogManager.getLogger();

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Order> getOrdersByUser(String username, int currentIndex, int itemsOnPage) throws DaoException {
        List<Order> orders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
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
            throw new DaoException("Error get Orders By User", throwables);
        }
        return orders;
    }

    private Order createInstanceOfOrder(Connection connection, ResultSet ordersSet) throws DaoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE_TIME);
        Map<Product, Long> products;
        Order order;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_DETAILS)) {
            int orderId = ordersSet.getInt(ORDERS_ORDER_ID);
            statement.setInt(1, orderId);
            ResultSet productsSet = statement.executeQuery();
            products = createMapOfProducts(productsSet);
            String username = ordersSet.getString(ORDERS_USERNAME);
            LocalDateTime orderDate = LocalDateTime.parse(ordersSet.getTimestamp(ORDERS_ORDER_DATE).toString(), formatter);
            //TODO WILL CHECK: LocalDateTime orderDate = LocalDateTime.parse(ordersSet.getString(ORDERS_ORDER_DATE), formatter);
            BigDecimal cost = ordersSet.getBigDecimal(ORDERS_ORDER_COST);
            Status status = Status.valueOf(ordersSet.getString(ORDERS_STATUS));
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
            while (!productsSet.next()) {
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
            product.setProductId(productsSet.getLong(PRODUCTS_PRODUCT_ID));
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
    public int countOrders() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
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
        return INVALID_COUNT;
    }

    @Override
    public int countOrdersOfUser(String username) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
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
        return INVALID_COUNT;
    }

    @Override
    public int createOrder(Order order) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            int orderId = insertInTableOrders(connection, order);
            insertInTableOrdersDetails(connection, order, orderId);
            connection.commit();
            return orderId;
        } catch (
                SQLException throwables) {
            logger.error("Error creating new order  ", throwables);
            throw new DaoException("Error creating new order  ", throwables);
        }
    }

    private void insertInTableOrdersDetails(Connection connection, Order order, int orderId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDERS_DETAILS)) {
            for (Map.Entry<Product, Long> product : order.getListOfProducts().entrySet()) {
                statement.setInt(INSERT_ORDERS_DETAILS_ID_COLUMN, orderId);
                statement.setLong(INSERT_ORDERS_DETAILS_PRODUCT_ID_COLUMN, product.getKey().getProductId());
                statement.setLong(INSERT_ORDERS_DETAILS_NUMBER_OF_PRODUCTS, product.getValue());
                statement.executeUpdate();
            }
        } catch (SQLException throwables) {
            logger.error("Error inserting rows in the table Order_details", throwables);
            throw new DaoException("Error inserting rows in the table Order_details", throwables);
        }
    }

    private int insertInTableOrders(Connection connection, Order order) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(INSERT_ORDERS_USERNAME_COLUMN, order.getUserName());
            preparedStatement.setTimestamp(INSERT_ORDERS_DATE_COLUMN, Timestamp.valueOf(order.getOrderDateTime()));
            preparedStatement.setBigDecimal(INSERT_ORDERS_COST_COLUMN, order.getOrderCost());
            preparedStatement.setString(INSERT_ORDERS_STATUS_CONFIRM_COLUMN, String.valueOf(order.getStatus()));
            if (preparedStatement.executeUpdate() > 1) {
                return getKey(preparedStatement);
            }
        } catch (SQLException throwables) {
            logger.error("Error inserting row in the table Order", throwables);
            throw new DaoException("Error inserting row in the table Order", throwables);
        }
        return INVALID_ID;
    }

    private int getKey(PreparedStatement preparedStatement) throws DaoException {
        try (ResultSet key = preparedStatement.getGeneratedKeys()) {
            if (key.next()) {
                return key.getInt(1);
            }
        } catch (SQLException throwables) {
            logger.error("Error getting key after inserting row in a table", throwables);
            throw new DaoException("Error getting key after inserting row in a table", throwables);
        }
        return INVALID_ID;
    }

    @Override
    public boolean deleteOrder(int orderId) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            deleteInTableOrdersDetails(connection, orderId);
            deleteRowInTableOrders(connection, orderId);
            connection.commit();
            return true;
        } catch (SQLException throwables) {
            logger.error("Error deleting a order " + orderId, throwables);
            throw new DaoException("Error deleting a order", throwables);
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
    public List<Order> getAll(int currentIndex, int itemsOnPage) throws DaoException {
        List<Order> orders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS)) {
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
        ConnectionPool connectionPool = ConnectionPool.getInstance();
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
    public boolean updateOrder(Order order) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {
            statement.setString(INSERT_ORDERS_USERNAME_COLUMN, order.getUserName());
            statement.setTimestamp(INSERT_ORDERS_DATE_COLUMN, Timestamp.valueOf(order.getOrderDateTime()));
            statement.setBigDecimal(INSERT_ORDERS_COST_COLUMN, order.getOrderCost());
            statement.setString(INSERT_ORDERS_STATUS_CONFIRM_COLUMN, order.getStatus().toString());
            statement.setLong(UPDATE_ORDER_ID_COLUMN, order.getOrderId());
        } catch (SQLException throwables) {
            logger.error("Error updating order by id", throwables);
            throw new DaoException("Error updating order by id", throwables);
        }
        return false;
    }
}



