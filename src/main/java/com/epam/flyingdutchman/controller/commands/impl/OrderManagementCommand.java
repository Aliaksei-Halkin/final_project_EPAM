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

import java.util.Comparator;
import java.util.List;

import static com.epam.flyingdutchman.controller.commands.util.Paginator.ITEMS_ON_PAGE;
import static com.epam.flyingdutchman.util.constants.Context.*;


public class OrderManagementCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            int currentPage = Paginator.getCurrentPage(request);
            int currentIndex = Paginator.countCurrentIndex(currentPage);
            List<Order> orders = orderService.findOrdersWithoutStatusClose(currentIndex, ITEMS_ON_PAGE);
           // orders.sort(Comparator.comparing(Order::getOrderDateTime).reversed());
            int numberOfOrders = orderService.countOrdersWithoutStatusClose();
            int numberOfPages = Paginator.countNumberOfPages(numberOfOrders);
            request.setAttribute(REQUEST_PAGE, currentPage);
            request.setAttribute(REQUEST_CURRENT_INDEX, currentIndex);
            request.setAttribute(REQUEST_ORDERS, orders);
            request.setAttribute(REQUEST_NUMBER_OF_ITEMS, numberOfOrders);
            request.setAttribute(REQUEST_NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(REQUEST_PAGINATOR_COMMAND, request.getParameter(REQUEST_COMMAND));
        } catch (ServiceException e) {
            logger.error("Error building list of orders", e);
        }
        return ConfigurationManager.getProperty("page.orderManagement");
    }
}
