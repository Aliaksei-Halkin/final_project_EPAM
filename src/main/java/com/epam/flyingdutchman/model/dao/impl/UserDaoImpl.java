package com.epam.flyingdutchman.model.dao.impl;

import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.DaoException;
import com.epam.flyingdutchman.model.connection.ConnectionPool;
import com.epam.flyingdutchman.model.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.flyingdutchman.util.constants.DatabaseColumn.*;
import static com.epam.flyingdutchman.util.constants.DatabaseQuery.*;

public class UserDaoImpl implements UserDao {
    private final Logger logger = LogManager.getLogger();
    private static final UserDaoImpl INSTANCE = new UserDaoImpl();
    private static final int UPDATE_USER_PASSWORD_COLUMN = 1;
    private static final int UPDATE_USER_FIRST_NAME_COLUMN = 2;
    private static final int UPDATE_USER_LAST_NAME_COLUMN = 3;
    private static final int UPDATE_USER_PHONE_COLUMN = 4;
    private static final int UPDATE_USER_EMAIL_COLUMN = 5;
    private static final int UPDATE_USER_ROLE_COLUMN = 6;
    private static final int UPDATE_USER_ACTIVE_COLUMN = 7;
    private static final int UPDATE_USER_USERNAME_COLUMN = 8;
    private static final int LIMIT_CURRENT_INDEX = 1;
    private static final int LIMIT_ON_PAGE_INDEX = 2;
    private static final int INVALID_COUNT = -1;
    private static final int USERS_USERNAME_INDEX = 1;
    private static final int USERS_PASSWORD_INDEX = 2;
    private static final int USERS_FIRST_NAME_INDEX = 3;
    private static final int USERS_LAST_NAME_INDEX = 4;
    private static final int USERS_PHONE_INDEX = 5;
    private static final int USERS_EMAIL_INDEX = 6;

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean addUser(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(USERS_USERNAME_INDEX, user.getUserName());
            statement.setString(USERS_PASSWORD_INDEX, user.getPassword());
            statement.setString(USERS_FIRST_NAME_INDEX, user.getFirstName());
            statement.setString(USERS_LAST_NAME_INDEX, user.getFirstName());
            statement.setString(USERS_PHONE_INDEX, user.getPhoneNumber());
            statement.setString(USERS_EMAIL_INDEX, user.geteMail());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error addinprivate w user to the database", e);
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) throws DaoException {
        User user = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createInstanceOfUser(resultSet);
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error("Error getting user bu ID", e);
            throw new DaoException("Error getting user bu ID in the database", e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findUserByPhone(String phoneNumber) throws DaoException {
        User user = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_PHONE)) {
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createInstanceOfUser(resultSet);
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error("Error getting user by phone", e);
            throw new DaoException("Error getting user by phone in the database", e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findUserByEmail(String eMail) throws DaoException {
        User user = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            statement.setString(1, eMail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createInstanceOfUser(resultSet);
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error("Error getting user by eMail", e);
            throw new DaoException("Error getting user by eMail in the database", e);
        }
        return Optional.ofNullable(user);
    }

    private User createInstanceOfUser(@NotNull ResultSet resultSet) throws DaoException {
        String username, password, firstName, lastName, phone, eMail;
        try {
            username = resultSet.getString(USERS_USERNAME);
            password = resultSet.getString(USERS_PASSWORD);
            firstName = resultSet.getString(USERS_FIRST_NAME);
            lastName = resultSet.getString(USERS_LAST_NAME);
            phone = resultSet.getString(USERS_PHONE);
            eMail = resultSet.getString(USERS_EMAIL);
        } catch (SQLException e) {
            logger.error("Error creating new user", e);
            throw new DaoException("Error creating new user", e);
        }
        return new User(username, password, firstName, lastName, phone, eMail);
    }

    @Override
    public boolean updateUser(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(UPDATE_USER_PASSWORD_COLUMN, user.getPassword());
            statement.setString(UPDATE_USER_FIRST_NAME_COLUMN, user.getFirstName());
            statement.setString(UPDATE_USER_LAST_NAME_COLUMN, user.getLastName());
            statement.setString(UPDATE_USER_PHONE_COLUMN, user.getPhoneNumber());
            statement.setString(UPDATE_USER_EMAIL_COLUMN, user.geteMail());
            statement.setInt(UPDATE_USER_ROLE_COLUMN, user.getUserRole());
            statement.setBoolean(UPDATE_USER_ACTIVE_COLUMN, user.getActive());
            statement.setString(UPDATE_USER_USERNAME_COLUMN, user.getUserName());
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error updating the user in the database", e);
        }
        return false;
    }

    @Override
    public List<User> findAllUsers(int currentIndex, int itemsOnPage) throws DaoException {
        List<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS)) {
            statement.setInt(LIMIT_CURRENT_INDEX, currentIndex);
            statement.setInt(LIMIT_ON_PAGE_INDEX, itemsOnPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(createInstanceOfUser(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting  all users from the database", e);
            throw new DaoException("Error getting all users from the database", e);
        }
        return users;
    }

    @Override
    public int countUsers() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(COUNT_USERS)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error counting all active users", e);
        }
        return INVALID_COUNT;
    }

    @Override
    public boolean validateUserCredentials(String username, String password) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_CREDENTIALS)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("Error counting all active users", e);
        }
        return false;
    }
}
