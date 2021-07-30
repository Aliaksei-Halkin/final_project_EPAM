package com.epam.flyingdutchman.model.service.impl;

import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.entity.Status;
import com.epam.flyingdutchman.exception.DaoException;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.dao.OrderDao;
import com.epam.flyingdutchman.model.dao.impl.OrderDaoImpl;
import com.epam.flyingdutchman.model.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao = OrderDaoImpl.getInstance();

    public OrderServiceImpl() {
    }

    @Override
    public List<Order> findOrdersOfUser(String username, int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return orderDao.findOrdersByUser(username, currentIndex, itemsOnPage);
        } catch (DaoException e) {
            throw new ServiceException("Error finding orders of user", e);
        }
    }

    @Override
    public int countOrdersOfUser(String username) throws ServiceException {
        try {
            return orderDao.countOrdersOfUser(username);
        } catch (DaoException e) {
            throw new ServiceException("Error counting orders of user", e);
        }
    }

    @Override
    public boolean createOrder(Order order) throws ServiceException {
        try {
            if (order.getUserName() == null) {
                return false;
            }
            return orderDao.createOrder(order) > 0;
        } catch (DaoException e) {
            throw new ServiceException("Error creating Order", e);
        }
    }

    @Override
    public boolean removeOrder(int orderId) throws ServiceException {
        try {
            return orderDao.deleteOrder(orderId);
        } catch (DaoException e) {
            throw new ServiceException("Error deleting order of user", e);
        }
    }

    @Override
    public List<Order> findOrders(int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return orderDao.findAllOrders(currentIndex, itemsOnPage);
        } catch (DaoException e) {
            throw new ServiceException("Error finding all orders", e);
        }
    }

    @Override
    public int countOrders() throws ServiceException {
        try {
            return orderDao.countOrders();
        } catch (DaoException e) {
            throw new ServiceException("Error counting orders", e);
        }
    }

    @Override
    public boolean changeOrderStatus(int orderId, Status status) throws ServiceException {
        Order order;
        try {
            order = orderDao.getOrderById(orderId);
            order.setStatus(status);
            return orderDao.updateOrder(order);
        } catch (DaoException e) {
            throw new ServiceException("Error changing order's status", e);
        }
    }
}
