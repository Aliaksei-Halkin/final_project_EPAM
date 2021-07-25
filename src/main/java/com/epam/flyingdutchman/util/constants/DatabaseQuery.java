package com.epam.flyingdutchman.util.constants;

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
    public static final String INSERT_USER = "INSERT INTO users (username, password, first_Name, last_Name, phone_number, e_mail) VALUES (?,?,?,?,?,?)";
    public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    public static final String SELECT_ALL_USERS = "SELECT * FROM users WHERE active = TRUE LIMIT ?, ?";
    public static final String COUNT_USERS = "SELECT COUNT(*) FROM users WHERE active = TRUE";
    public static final String UPDATE_USER = "UPDATE users SET password = ?, "
            + "first_name = ?, last_name = ?, phone_number = ?, e_mail = ?, "
            + "user_role = ?, active = ? WHERE username = ?";
    public static final String SELECT_USER_BY_CREDENTIALS =
            "SELECT * FROM users WHERE username = ? AND password = ? AND active = TRUE";

    private DatabaseQuery() {
    }
}
