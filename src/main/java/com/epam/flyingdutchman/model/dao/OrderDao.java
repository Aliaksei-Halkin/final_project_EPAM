package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.exception.DaoException;

import java.util.List;

public interface OrderDao {
    List<Order> getOrdersByUser(String username, int currentIndex, int itemsOnPage) throws DaoException;

    int countOrders() throws DaoException;

    int countOrdersOfUser(String username) throws DaoException;

    int createOrder(Order order) throws DaoException;

    boolean deleteOrder(int orderId) throws DaoException;

    List<Order> getAll(int currentIndex, int itemsOnPage) throws DaoException;

    Order getOrderById(int orderId) throws DaoException;

    boolean updateOrder(Order order) throws DaoException;
}
