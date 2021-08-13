package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
//fixme will do BaseDao
public interface OrderDao extends BaseDao {
    List<Order> findOrdersByUser(String username, int currentIndex, int itemsOnPage) throws DaoException;

    int countOrdersWithoutStatusClose() throws DaoException;

    int countOrdersOfUser(String username) throws DaoException;

    int createOrder(Order order) throws DaoException, SQLException;

    boolean deleteOrder(int orderId) throws DaoException, SQLException;

    List<Order> findAllOrdersWithoutStatusClose(int currentIndex, int itemsOnPage) throws DaoException;

    Order getOrderById(int orderId) throws DaoException;

    boolean update(Order order) throws DaoException;
}
