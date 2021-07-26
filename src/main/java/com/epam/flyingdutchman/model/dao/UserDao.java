package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    boolean addUser(User user) throws DaoException;

    Optional<User> getByUsername(String username) throws DaoException;

    Optional<User> getByPhone(String username) throws DaoException;

    Optional<User> getByEmail(String eMail) throws DaoException;

    boolean updateUser(User user) throws DaoException;

    List<User> getAll(int currentIndex, int itemsOnPage) throws DaoException;

    int countUsers() throws DaoException;

    boolean validateUserCredentials(String username, String password) throws DaoException;
}
