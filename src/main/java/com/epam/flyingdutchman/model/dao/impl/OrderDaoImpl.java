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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.ToDoubleBiFunction;

import static com.epam.flyingdutchman.util.constants.DatabaseColumn.*;
import static com.epam.flyingdutchman.util.constants.DatabaseQuery.SELECT_ORDERS_BY_USER;
import static com.epam.flyingdutchman.util.constants.DatabaseQuery.SELECT_ORDERS_DETAILS;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class OrderDaoImpl implements OrderDao {
    private static final int SELECT_BY_USER_USERNAME_INDEX = 1;
    private static final int SELECT_BY_USER_LIMIT_CURRENT_INDEX = 2;
    private static final int SELECT_BY_USER_LIMIT_ON_PAGE_INDEX = 3;
    private static final int INSERT_ORDER_ID_COLUMN = 1;
    private static final int INSERT_ORDER_PRODUCT_ID_COLUMN = 2;
    private static final int INSERT_ORDERS_NUMBER_OF_PRODUCTS = 3;
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
    private static OrderDaoImpl INSTANCE = new OrderDaoImpl();
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
            LocalDateTime orderDate = LocalDateTime.parse((CharSequence) ordersSet.getTimestamp(ORDERS_ORDER_DATE), formatter);
            //TODO WILL CHECK: LocalDateTime orderDate = LocalDateTime.parse(ordersSet.getString(ORDERS_ORDER_DATE), formatter);
            BigDecimal cost = ordersSet.getBigDecimal(ORDERS_ORDER_COST);
            Status status = Status.valueOf(ordersSet.getString(ORDERS_STATUS));
            order = new Order(orderId, username, orderDate, cost, status, products);
            productsSet.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error getting a product from ResultSet", e);
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
            throw new DaoException("Error getting a product from ResultSet", e);
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
            throw new DaoException("Error getting a product from ResultSet", e);
        }
        return product;
    }
    public int countOrders(){

    }


}
