package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.OrderService;
import com.epam.flyingdutchman.model.service.impl.OrderServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.epam.flyingdutchman.controller.commands.util.Paginator.ITEMS_ON_PAGE;
import static com.epam.flyingdutchman.util.constants.Context.*;

public class OrdersCommand implements Command {
    private final Logger logger = LogManager.getLogger();
    OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            Integer currentPage = Paginator.getCurrentPage(request);
            request.setAttribute(REQUEST_PAGE, currentPage);
            int currentIndex = Paginator.countCurrentIndex(currentPage);
            request.setAttribute(REQUEST_CURRENT_INDEX, currentIndex);
            String username = (String) request.getSession().getAttribute(SESSION_USERNAME);
            List<Order> orders = orderService.findOrdersOfUser(username, currentIndex, ITEMS_ON_PAGE);
            request.setAttribute(REQUEST_ORDERS, orders);
            int numberOfOrders = orderService.countOrdersOfUser(username);
            request.setAttribute(REQUEST_NUMBER_OF_ITEMS, numberOfOrders);
            int numberOfPages = Paginator.countNumberOfPages(numberOfOrders);
            request.setAttribute(REQUEST_NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(REQUEST_PAGINATOR_COMMAND, request.getParameter(REQUEST_COMMAND));
        } catch (ServiceException e) {
            logger.error("Error in MakeOrderCommand", e);
        }
        return ConfigurationManager.getProperty("page.history");
    }
}
