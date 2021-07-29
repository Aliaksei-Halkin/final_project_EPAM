package com.epam.flyingdutchman.model.service;

import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.entity.Status;
import com.epam.flyingdutchman.exception.ServiceException;

import java.util.List;

public interface OrderService {
    List<Order> findOrdersOfUser(String username, int currentIndex, int itemsOnPage) throws ServiceException;

    int countOrdersOfUser(String username) throws ServiceException;

    boolean createOrder(Order order) throws ServiceException;

    boolean removeOrder(int orderId) throws ServiceException;

    List<Order> findOrders(int currentIndex, int itemsOnPage) throws ServiceException;

    int countOrders() throws ServiceException;

    boolean changeOrderStatus(int orderId, Status status) throws ServiceException;
}
