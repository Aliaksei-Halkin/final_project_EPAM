package com.epam.flyingdutchman.model.dao.impl;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.DaoException;
import com.epam.flyingdutchman.model.connection.ConnectionPool;
import com.epam.flyingdutchman.model.dao.ProductDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.flyingdutchman.util.constants.DatabaseColumn.*;
import static com.epam.flyingdutchman.util.constants.DatabaseQuery.*;

public class ProductDaoImpl implements ProductDao {
    private static final ProductDaoImpl INSTANCE = new ProductDaoImpl();
    private static final int PRODUCT_NAME_INDEX = 1;
    private static final int PRODUCT_IMG_INDEX = 2;
    private static final int PRODUCT_COST_INDEX = 3;
    private static final int PRODUCT_DESCRIPTION_INDEX = 4;
    private static final int PRODUCT_ACTIVE_INDEX = 5;
    private static final int PRODUCT_ID_INDEX = 6;
    private static final int SEARCH_PATTERN_INDEX = 1;
    private static final int SELECT_SEARCH_LIMIT_CURRENT_INDEX = 2;
    private static final int SELECT_SEARCH_LIMIT_ON_PAGE_INDEX = 3;
    private static final String ANY_CHAR = "%";
    private static final int INVALID_COUNT = -1;
    private static final int INVALID_ID = -1;
    private static final int SELECT_ALL_LIMIT_CURRENT_INDEX = 1;
    private static final int SELECT_ALL_LIMIT_ON_PAGE_INDEX = 2;
    private final Logger logger = LogManager.getLogger();

    private ProductDaoImpl() {
    }

    public static ProductDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public int saveProduct(Product product) throws DaoException {
        int idProduct = INVALID_ID;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(PRODUCT_NAME_INDEX, product.getName());
            statement.setString(PRODUCT_IMG_INDEX, product.getProductImgPath());
            statement.setBigDecimal(PRODUCT_COST_INDEX, product.getCost());
            statement.setString(PRODUCT_DESCRIPTION_INDEX, product.getDescription());
            statement.setBoolean(PRODUCT_ACTIVE_INDEX, product.isActive());
            if (statement.executeUpdate() == 1) {
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        idProduct = keys.getInt(1);
                    }
                } catch (SQLException e) {
                    logger.error("Didn't generate a ID ofproduct" + e.getMessage());
                    throw new DaoException("Didn't generate a ID ofproduct", e);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error adding a new product to the database", e);
        }
        return idProduct;
    }

    @Override
    public List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) throws DaoException {
        List<Product> products = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCTS_LIKE)) {
            String searchPattern = ANY_CHAR + searchString + ANY_CHAR;
            statement.setString(SEARCH_PATTERN_INDEX, searchPattern);
            statement.setInt(SELECT_SEARCH_LIMIT_CURRENT_INDEX, currentIndex);
            statement.setInt(SELECT_SEARCH_LIMIT_ON_PAGE_INDEX, itemsOnPage);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(createInstanceOfProduct(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error searching a word in the database", e);
        }
        return products;
    }

    @Override
    public int countSearchResults(String searchString) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_SEARCH_RESULTS)) {
            String searchPattern = ANY_CHAR + searchString + ANY_CHAR;
            statement.setString(1, searchPattern);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error counting words in the string query", e);
        }
        return INVALID_COUNT;
    }

    @Override
    public List<Product> findAllProducts(int currentIndex, int itemsOnPage) throws DaoException {
        List<Product> listOfAllProducts = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCTS)) {
            statement.setInt(SELECT_ALL_LIMIT_CURRENT_INDEX, currentIndex);
            statement.setInt(SELECT_ALL_LIMIT_ON_PAGE_INDEX, itemsOnPage);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    listOfAllProducts.add(createInstanceOfProduct(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error getting all products in the database ", e);
        }
        return listOfAllProducts;
    }

    @Override
    public int countProducts() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(COUNT_PRODUCTS)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error counting products in the string query", e);
        }
        return INVALID_COUNT;
    }

    @Override
    public Optional<Product> findProductById(int productId) throws DaoException {
        Product product = new Product();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product = createInstanceOfProduct(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error getting product by ID in the database", e);
        }
        return Optional.of(product);
    }

    @Override
    public boolean updateProduct(Product product) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT)) {
            statement.setString(PRODUCT_NAME_INDEX, product.getName());
            statement.setString(PRODUCT_IMG_INDEX, product.getProductImgPath());
            statement.setBigDecimal(PRODUCT_COST_INDEX, product.getCost());
            statement.setString(PRODUCT_DESCRIPTION_INDEX, product.getDescription());
            statement.setBoolean(PRODUCT_ACTIVE_INDEX, product.isActive());
            statement.setLong(PRODUCT_ID_INDEX, product.getProductId());
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error updating product in the database", e);
        }
        return false;
    }

    private Product createInstanceOfProduct(ResultSet resultSet) throws DaoException {
        Product product = new Product();
        try {
            product.setProductId(resultSet.getLong(PRODUCTS_PRODUCT_ID));
            product.setName(resultSet.getString(PRODUCTS_PRODUCT_NAME));
            product.setProductImgPath(resultSet.getString(PRODUCTS_IMAGE_PATH));
            product.setCost(resultSet.getBigDecimal(PRODUCTS_COST));
            product.setDescription(resultSet.getString(PRODUCTS_DESCRIPTION));
            product.setActive(resultSet.getBoolean(PRODUCTS_ACTIVE));
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error taking product from the database", e);
        }
        return product;
    }
}
