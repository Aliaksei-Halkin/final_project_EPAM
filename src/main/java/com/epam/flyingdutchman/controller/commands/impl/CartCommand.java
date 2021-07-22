package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.model.service.impl.CartService;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.flyingdutchman.util.constants.Context.*;

public class CartCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Product> products = (ArrayList<Product>) session.getAttribute(SESSION_CART);
        if (products != null) {
            Map<Product, Long> productsGrouped = CartService.groupProducts(products);
            request.setAttribute(REQUEST_GROUPED_PRODUCTS, new ArrayList<>(productsGrouped.entrySet()));
            BigDecimal cost = CartService.countTotalCost(products);
            request.setAttribute(REQUEST_CART_TOTAL_COST, cost.toString());
            String orderStatus = request.getParameter(REQUEST_ORDER_STATUS);
            request.setAttribute(REQUEST_ORDER_STATUS, orderStatus);
        }
        return ConfigurationManager.getProperty("page.cart");
    }
}
