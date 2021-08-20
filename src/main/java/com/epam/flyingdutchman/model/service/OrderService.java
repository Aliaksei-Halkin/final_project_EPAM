package com.epam.flyingdutchman.model.service;

import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.entity.Status;
import com.epam.flyingdutchman.exception.ServiceException;

import java.util.List;
/**
 * The interface represents {@code Order} service
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public interface OrderService {
    List<Order> findOrdersOfUser(String username, int currentIndex, int itemsOnPage) throws ServiceException;

    int countOrdersOfUser(String username) throws ServiceException;

    boolean createOrder(Order order) throws ServiceException;

    void removeOrder(int orderId) throws ServiceException;

    List<Order> findOrdersWithoutStatusClose(int currentIndex, int itemsOnPage) throws ServiceException;

    int countOrdersWithoutStatusClose() throws ServiceException;

    void changeOrderStatus(int orderId, Status status) throws ServiceException;
}
