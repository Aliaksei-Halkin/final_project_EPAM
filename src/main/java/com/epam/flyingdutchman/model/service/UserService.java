package com.epam.flyingdutchman.model.service;

import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.ServiceException;

import java.util.List;

public interface UserService {
    boolean registerNewUser(User user) throws ServiceException;

    boolean changeRole(User user, int role) throws ServiceException;

    List<User> findAll(int currentIndex, int itemsOnPage) throws ServiceException;

    int countUsers() throws ServiceException;

    boolean deleteUser(String username) throws ServiceException;

    User findUserByUsername(String userName) throws ServiceException;

    boolean checkIfUsernameFree(String username) throws ServiceException;

    boolean checkIfPhoneFree(String phone) throws ServiceException;

    boolean checkIfEmailFree(String eMail) throws ServiceException;

    boolean checkIfValidUser(String username, String password) throws ServiceException;

    boolean updateUser(User user) throws ServiceException;
}
