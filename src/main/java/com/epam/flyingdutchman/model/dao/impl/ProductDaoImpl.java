package com.epam.flyingdutchman.model.dao.impl;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.model.connection.ConnectionPool;
import com.epam.flyingdutchman.model.dao.ProductDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.epam.flyingdutchman.model.dao.query.DatabaseQuery.INSERT_PRODUCT;

public class ProductDaoImpl implements ProductDao {
    private static final ProductDaoImpl INSTANCE = new ProductDaoImpl();
    private static final int SEARCH_PATTERN_INDEX = 1;
    private static final int SELECT_SEARCH_LIMIT_CURRENT_INDEX = 2;
    private static final int SELECT_SEARCH_LIMIT_ON_PAGE_INDEX = 3;
    private static final int PRODUCT_NAME_INDEX = 1;
    private static final int PRODUCT_IMG_INDEX = 2;
    private static final int PRODUCT_COST_INDEX = 3;
    private static final int PRODUCT_DESCRIPTION_INDEX = 4;
    private static final int PRODUCT_ACTIVE_INDEX = 5;
    private static final int PRODUCT_ID_INDEX = 6;
    private static final String ANY_CHAR = "%";
    private static final int INVALID_COUNT = -1;
    private static final int INVALID_ID = -1;

    private ProductDaoImpl() {
    }

    @Override
    public int save(Product product) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try(Connection connection=connectionPool.getConnection();
            PreparedStatement statement =connection.prepareStatement(INSERT_PRODUCT)) {
            statement.setString(PRODUCT_NAME_INDEX, product.getName());


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) {
        return null;
    }

    @Override
    public int countSearchResults(String searchString) {
        return 0;
    }

    @Override
    public List<Product> getAll(int currentIndex, int itemsOnPage) {
        return null;
    }

    @Override
    public int countProducts() {
        return 0;
    }

    @Override
    public Product getById(int productId) {
        return null;
    }

    @Override
    public boolean update(Product product) {
        return false;
    }
}
