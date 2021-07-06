package com.epam.flyingdutchman.model.dao.query;

public class DatabaseQuery {
    public static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products WHERE active = TRUE LIMIT ?, ?";
    public static final String COUNT_PRODUCTS = "SELECT COUNT(*) FROM products WHERE active = TRUE";
    public static final String COUNT_SEARCH_RESULTS = "SELECT COUNT(*) FROM products WHERE product_name LIKE ? AND active = TRUE";
    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM products WHERE product_id = ?";
    public static final String SELECT_PRODUCTS_LIKE = "SELECT * FROM products WHERE product_name LIKE ? AND active = TRUE LIMIT ?, ?";
    public static final String UPDATE_PRODUCT = "UPDATE products SET product_name = ?, " +
            "image_path = ?, cost = ?, description = ?, active = ? WHERE product_id = ?";
    public static final String INSERT_PRODUCT = "INSERT INTO products "
            + "(product_name, image_path, cost, description, active) VALUES (?, ?, ?, ?, ?)";

    private DatabaseQuery() {
    }
}
