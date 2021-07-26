package com.epam.flyingdutchman.model.service;

import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.ServiceException;

import java.util.List;

public interface UserService {
    boolean registerNewUser(User user) throws ServiceException;

    boolean setUserCustomerRole(User user) throws ServiceException;

    boolean setUserManagerRole(User user) throws ServiceException;

    boolean setUserCookRole(User user) throws ServiceException;

    List<User> getAll(int currentIndex, int itemsOnPage) throws ServiceException;

    int countUsers() throws ServiceException;

    boolean deleteUser(String username) throws ServiceException;

    User getUserByUsername(String userName) throws ServiceException;

    boolean isUsernameFree(String username);

    boolean isPhoneFree(String phone);

    boolean isEmailFree(String eMail);

    boolean isValidUser(String username, String password) throws ServiceException;
}
