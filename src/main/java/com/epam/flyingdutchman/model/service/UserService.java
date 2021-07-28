package com.epam.flyingdutchman.model.service;

import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.ServiceException;

import java.util.List;

public interface UserService {
    boolean registerNewUser(User user) throws ServiceException;

    boolean setUserCustomerRole(User user) throws ServiceException;

    boolean setUserManagerRole(User user) throws ServiceException;

    boolean setUserCookRole(User user) throws ServiceException;

    List<User> findAll(int currentIndex, int itemsOnPage) throws ServiceException;

    int countUsers() throws ServiceException;

    boolean deleteUser(String username) throws ServiceException;

    User findUserByUsername(String userName) throws ServiceException;

    boolean checkIfUsernameFree(String username);

    boolean checkIfPhoneFree(String phone);

    boolean checkIfEmailFree(String eMail);

    boolean checkIfValidUser(String username, String password) throws ServiceException;
}
