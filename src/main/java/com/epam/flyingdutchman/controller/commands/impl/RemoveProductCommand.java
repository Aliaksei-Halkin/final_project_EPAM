package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.RequestProcessor;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.impl.CartService;
import com.epam.flyingdutchman.model.service.impl.ProductServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_PRODUCT;
import static com.epam.flyingdutchman.util.constants.Context.SESSION_CART;

public class RemoveProductCommand implements Command {
    private final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            int productId = RequestProcessor.getIntFromRequest(request, REQUEST_PRODUCT);
            Product product = new ProductServiceImpl().findProductById(productId);
            List<Product> cart = (ArrayList<Product>) session.getAttribute(SESSION_CART);
            CartService.removeProduct(cart, product);
            session.setAttribute(SESSION_CART, cart);
        } catch (ServiceException e) {
            logger.error("Error removing product from cart", e);
        }
        return ConfigurationManager.getProperty("page.cartRedirect");
    }
}
