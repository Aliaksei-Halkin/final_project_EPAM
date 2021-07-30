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
    public static final String SELECT_USER_BY_PHONE = "SELECT * FROM users WHERE phone_number = ?";
    public static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE e_mail = ?";
    public static final String SELECT_USER_BY_CREDENTIALS = "SELECT * FROM users WHERE username = ? AND password = ?";
    public static final String SELECT_ORDERS_BY_USER = "SELECT * FROM orders WHERE username = ? LIMIT ?,?";
    public static final String SELECT_ORDERS_DETAILS = "SELECT * FROM orders_details "
            + "LEFT JOIN products ON orders_details.product_id = products.product_id "
            + "WHERE orders_details.order_id = ?";
    public static final String INSERT_ORDERS_DETAILS = "INSERT INTO orders_details VALUES (?, ?, ?)";
        public static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ?";
    public static final String DELETE_ORDERS_DETAILS = "DELETE FROM orders_details WHERE order_id = ?";
    public static final String SELECT_IS_APPROVED = "SELECT confirmation_status FROM orders WHERE order_id = ?";
    public static final String SELECT_ALL_ORDERS = "SELECT * FROM orders LIMIT ?, ?";
    public static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ?";
    public static final String UPDATE_ORDER = "UPDATE orders SET username = ?, order_date = ?, order_cost = ?, "
            + "confirmation_status = ? WHERE order_id = ?";
    public static final String COUNT_ORDERS = "SELECT COUNT(*) FROM orders";
    public static final String COUNT_ORDERS_BY_USER = "SELECT COUNT(*) FROM orders WHERE username = ?";

    private DatabaseQuery() {
    }
}
