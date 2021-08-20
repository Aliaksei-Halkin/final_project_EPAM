package com.epam.flyingdutchman.model.service.impl;

import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.entity.Status;
import com.epam.flyingdutchman.exception.DaoException;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.dao.OrderDao;
import com.epam.flyingdutchman.model.dao.impl.OrderDaoImpl;
import com.epam.flyingdutchman.model.service.OrderService;

import java.sql.SQLException;
import java.util.List;

/**
 * The service class of model layer, intended to process operations with {@code Order}.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService {
    /**
     * DAO object to get stored information about committed {@code Order} and to save new
     * information to the storage.
     */
    private final OrderDao orderDao = OrderDaoImpl.getInstance();

    /**
     * The basic no-args constructor
     */
    public OrderServiceImpl() {
    }

    /**
     * Get {@code Order} of given {@code User}. Get only items for selected page determined by start
     * index and number of items on page
     *
     * @param username     {@code String} define certain {@code User}
     * @param currentIndex {@code int} value of start index to get info about user's orders.
     *                     Intended for paging option. Determined by the number of current page. Can
     *                     be calculated the next way:<p>currentIndex = (currentPageNumber - 1) *
     *                     itemsOnPage)</p> Accepted by LIMIT statement in SQL standard.
     * @param itemsOnPage  {@code int} number of items to display on one page. Intended to be used
     *                     for paging option. Accepted by LIMIT statement in SQL standard.
     * @return {@code List} of {@code Order} of given {@code User} from position data storage equals
     * {@code int} currentIndex, max length of list - no more than value of {@code int}
     * itemsOnPage.
     */
    @Override
    public List<Order> findOrdersOfUser(String username, int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return orderDao.findOrdersByUser(username, currentIndex, itemsOnPage);
        } catch (DaoException e) {
            throw new ServiceException("Error finding orders of user", e);
        }
    }

    /**
     * Count {@code Order} number for selected {@code User}.
     *
     * @param username {@code String} define certain {@code User}
     * @return {@code int} number of {@code Order} of selected {@code User}
     */
    @Override
    public int countOrdersOfUser(String username) throws ServiceException {
        try {
            return orderDao.countOrdersOfUser(username);
        } catch (DaoException e) {
            throw new ServiceException("Error counting orders of user", e);
        }
    }

    /**
     * Pass information about {@code Order} to DAO object to create a new entry and storey it.
     *
     * @param order instance of {@code Order}, containing all necessary information about user's
     *              order, that need to be stored
     * @return {@code true} if DAO object reports that entry or order was successfully created
     */
    @Override
    public boolean createOrder(Order order) throws ServiceException {
        try {
            if (order.getUserName() == null) {
                return false;
            }
            return orderDao.createOrder(order) > 0;
        } catch (DaoException | SQLException e) {
            throw new ServiceException("Error creating Order", e);
        }
    }

    /**
     * Remove information about user's order from storage via DAO object. Order is defined by unique
     * identification number.
     *
     * @param orderId {@code int} value of unique {@code Order} id
     */
    @Override
    public void removeOrder(int orderId) throws ServiceException {
        try {
            orderDao.deleteOrder(orderId);
        } catch (DaoException | SQLException e) {
            throw new ServiceException("Error deleting order of user", e);
        }
    }

    /**
     * Get {@code List} of {@code Order} without status "CLOSE" that represented in information storage. Get only items for
     * selected page determined by start index and number of items on page
     *
     * @param currentIndex {@code int} value of start index to get info about orders. Intended for
     *                     paging option. Determined by the number of current page. Can be
     *                     calculated the next way:
     *                     <p>currentIndex = (currentPageNumber -
     *                     1) * itemsOnPage)</p> Accepted by LIMIT statement in SQL standard.
     * @param itemsOnPage  {@code int} number of items to display on one page. Intended to be used
     *                     for paging option. Accepted by LIMIT statement in SQL standard.
     * @return {@code List} of {@code Order} from position in data storage that equals {@code int}
     * currentIndex, max length of list - no more than value of {@code int} itemsOnPage.
     */
    @Override
    public List<Order> findOrdersWithoutStatusClose(int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return orderDao.findAllOrdersWithoutStatusClose(currentIndex, itemsOnPage);
        } catch (DaoException e) {
            throw new ServiceException("Error finding all orders", e);
        }
    }

    /**
     * Count  {@code Order} without status "CLOSE"  in information storage.
     *
     * @return {@code int} number of {@code Order}
     */
    @Override
    public int countOrdersWithoutStatusClose() throws ServiceException {
        try {
            return orderDao.countOrdersWithoutStatusClose();
        } catch (DaoException e) {
            throw new ServiceException("Error counting orders", e);
        }
    }

    /**
     * Changes {@code Order} status for {@code Order} .
     *
     * @param orderId {@code int} value of unique {@code Order} id
     * @param status  {@code Status} represents the new status for {@code Order}
     */
    @Override
    public void changeOrderStatus(int orderId, Status status) throws ServiceException {
        Order order;
        try {
            order = orderDao.getOrderById(orderId);
            order.setStatus(status);
            orderDao.update(order);
        } catch (DaoException e) {
            throw new ServiceException("Error changing order's status", e);
        }
    }
}
