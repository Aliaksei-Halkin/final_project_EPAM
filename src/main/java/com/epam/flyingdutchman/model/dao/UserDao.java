package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.DaoException;

import java.util.List;

public interface UserDao {
    boolean addUser(User user) throws DaoException;

    User getByUsername(String username) throws DaoException;

    boolean updateUser(User user) throws DaoException;

    List<User> getAll(int currentIndex, int itemsOnPage) throws DaoException;

    int countUsers() throws DaoException;

    boolean validateUserCredentials(String username, String password);
}
