package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.entity.Order;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.entity.Status;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.OrderService;
import com.epam.flyingdutchman.model.service.impl.CartService;
import com.epam.flyingdutchman.model.service.impl.OrderServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.flyingdutchman.util.constants.Context.*;

public class MakeOrderCommand implements Command {
    private final Logger logger = LogManager.getLogger();
    OrderService orderService = new OrderServiceImpl();

//    public MakeOrderCommand(OrderService orderService) {
//        this.orderService = orderService;
//    } //todo check variants of realisation

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("page.error");
        String orderStatus;
        HttpSession session = request.getSession();
        Order order = createInstanceOfOrder(session);
        try {
            if (orderService.createOrder(order)) {
                clearCart(session);
                page = ConfigurationManager.getProperty("page.historyRedirect");
            } else {
                orderStatus = MessageManager.getMessage("msg.orderNotCreated");
                request.setAttribute(REQUEST_ORDER_STATUS, orderStatus);
                page = ConfigurationManager.getProperty("page.cartRedirect");
            }
        } catch (ServiceException e) {
            logger.error("Error in MakeOrderCommand", e);
        }
        return page;
    }

    private Order createInstanceOfOrder(HttpSession session) {
        Order order;
        List<Product> cart = (List<Product>) session.getAttribute(SESSION_CART);
        String username = (String) session.getAttribute(SESSION_USERNAME);
        BigDecimal orderCost = CartService.countTotalCost(cart);
        Map<Product, Long> productsMap = CartService.groupProducts(cart);
        order = new Order(username, orderCost, productsMap);
        return order;
    }

    private void clearCart(HttpSession session) {
        session.setAttribute(SESSION_CART, new ArrayList<Product>());
    }
}
