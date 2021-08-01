package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    List<Order> findOrdersByUser(String username, int currentIndex, int itemsOnPage) throws DaoException;

    int countOrders() throws DaoException;

    int countOrdersOfUser(String username) throws DaoException;

    int createOrder(Order order) throws DaoException, SQLException;

    boolean deleteOrder(int orderId) throws DaoException, SQLException;

    List<Order> findAllOrders(int currentIndex, int itemsOnPage) throws DaoException;

    Order getOrderById(int orderId) throws DaoException;

    boolean updateOrder(Order order) throws DaoException;
}
