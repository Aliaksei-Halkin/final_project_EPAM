package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    boolean addUser(User user) throws DaoException;

    Optional<User> findUserByUsername(String username) throws DaoException;

    Optional<User> findUserByPhone(String username) throws DaoException;

    Optional<User> findUserByEmail(String eMail) throws DaoException;

    List<User> findAllUsers(int currentIndex, int itemsOnPage) throws DaoException;

    int countUsers() throws DaoException;

    boolean validateUserCredentials(String username, String password) throws DaoException;
}
