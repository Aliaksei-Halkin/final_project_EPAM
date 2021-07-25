package com.epam.flyingdutchman.model.service.impl;

import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.DaoException;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.dao.impl.UserDaoImpl;
import com.epam.flyingdutchman.model.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDaoImpl userDao = UserDaoImpl.getInstance();
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
    public List<User> getAll(int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return userDao.getAll(currentIndex, itemsOnPage);
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
            optionalUser = userDao.getByUsername(username);
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
    public User getUserByUsername(String userName) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.getByUsername(userName);
            if (optionalUser.isEmpty()) {
                throw new ServiceException("ERROR: The user isn't finding");
            }
        } catch (DaoException e) {
            throw new ServiceException("error while deactivating user by username", e);
        }
        return optionalUser.get();
    }
    @Override
    public boolean isUsernameFree(String username) {
        Optional<User> optionalUser;
        boolean result = true;
        try {
            optionalUser = userDao.getByUsername(username);
            result = optionalUser.isEmpty();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean isPhoneFree(String phone) {
        Optional<User> optionalUser;
        boolean result = true;
        try {
            optionalUser = userDao.getByPhone(phone);
            result = optionalUser.isEmpty();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean isEmailFree(String eMail) {
        Optional<User> optionalUser;
        boolean result = true;
        try {
            optionalUser = userDao.getByEmail(eMail);
            result = optionalUser.isEmpty();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }
}
