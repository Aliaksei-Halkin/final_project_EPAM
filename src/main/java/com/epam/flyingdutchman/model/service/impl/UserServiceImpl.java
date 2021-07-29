package com.epam.flyingdutchman.model.service.impl;

import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.DaoException;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.dao.UserDao;
import com.epam.flyingdutchman.model.dao.impl.UserDaoImpl;
import com.epam.flyingdutchman.model.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = UserDaoImpl.getInstance();

    @Override
    public boolean registerNewUser(User user) throws ServiceException {
        try {
            return userDao.addUser(user);
        } catch (DaoException e) {
            throw new ServiceException("Error adding the user", e);
        }
    }

    @Override
    public boolean setUserCustomerRole(User user) throws ServiceException {
        changeRole(user, User.ROLE_CUSTOMER);
        return true;
    }

    private void changeRole(User user, int role) throws ServiceException {
        user.setUserRole(role);
        try {
            userDao.updateUser(user);
        } catch (DaoException e) {
            throw new ServiceException("Error changing user role", e);
        }
    }

    @Override
    public boolean setUserManagerRole(User user) throws ServiceException {
        changeRole(user, User.ROLE_MANAGER);
        return true;
    }

    @Override
    public boolean setUserCookRole(User user) throws ServiceException {
        changeRole(user, User.ROLE_COOK);
        return true;
    }

    @Override
    public List<User> findAll(int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return userDao.findAllUsers(currentIndex, itemsOnPage);
        } catch (DaoException e) {
            throw new ServiceException("Error getting all users", e);
        }
    }

    @Override
    public int countUsers() throws ServiceException {
        try {
            return userDao.countUsers();
        } catch (DaoException e) {
            throw new ServiceException("Error counting all users", e);
        }
    }

    @Override
    public boolean deleteUser(String username) throws ServiceException {
        Optional<User> optionalUser;
        User user;
        try {
            optionalUser = userDao.findUserByUsername(username);
            if (optionalUser.isEmpty()) {
                throw new ServiceException("The user isn't finding, error");
            }
            user = optionalUser.get();
            user.setActive(false);
            userDao.updateUser(user);
        } catch (DaoException e) {
            throw new ServiceException("error while deactivating user by username", e);
        }
        return true;
    }

    @Override
    public User findUserByUsername(String userName) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findUserByUsername(userName);
            if (optionalUser.isEmpty()) {
                throw new ServiceException("ERROR: The user isn't finding");
            }
        } catch (DaoException e) {
            throw new ServiceException("error while deactivating user by username", e);
        }
        return optionalUser.get();
    }

    /**
     * Searching the username in the database and validating
     *
     * @param username the username
     * @return {@code true} If a value is not present, otherwise -false
     */
    @Override
    public boolean checkIfUsernameFree(String username) {
        Optional<User> optionalUser;
        boolean result = false;
        try {
            optionalUser = userDao.findUserByUsername(username);
            result = optionalUser.isEmpty();
        } catch (DaoException e) {
            e.printStackTrace();//fixme will add serviceexception
        }
        return result;
    }

    /**
     * Searching the phone in the database and validating
     *
     * @param phone the phone
     * @return {@code true} If a value is not present, otherwise -false
     */
    @Override
    public boolean checkIfPhoneFree(String phone) {
        Optional<User> optionalUser;
        boolean result = false;
        try {
            optionalUser = userDao.findUserByPhone(phone);
            result = optionalUser.isEmpty();
        } catch (DaoException e) {
            e.printStackTrace();//fixme will add serviceexception
        }
        return result;
    }

    /**
     * Searching the email in the database and validating
     *
     * @param eMail the email
     * @return {@code true} If a value is not present, otherwise -false
     */
    @Override
    public boolean checkIfEmailFree(String eMail) {
        Optional<User> optionalUser;
        boolean result = false;
        try {
            optionalUser = userDao.findUserByEmail(eMail);
            result = optionalUser.isEmpty();
        } catch (DaoException e) {
            e.printStackTrace();//fixme will add serviceexception
        }
        return result;
    }
    /**
     * Validate users credentials. User considered valid if DAO object contains information that
     * there is active user account with such username and password
     *
     * @param username {@code String} represents login (username) of the user's account
     * @param password {@code String} represents password of the user's account. Should be in form
     *                 that it was provided to the DAO object (encrypted if it stored encrypted)
     * @return {@code true} if user is valid
     */
    @Override
    public boolean checkIfValidUser(String username, String password) throws ServiceException {
        try {
            return userDao.validateUserCredentials(username, password);
        } catch (DaoException e) {
            throw new ServiceException("error while deactivating user by username", e);
        }
    }
}
