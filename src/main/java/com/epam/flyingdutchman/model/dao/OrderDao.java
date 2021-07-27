package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.exception.DaoException;

import java.util.List;

public interface OrderDao {
    List<Order> getOrdersByUser(String username, int currentIndex, int itemsOnPage) throws DaoException;
}
