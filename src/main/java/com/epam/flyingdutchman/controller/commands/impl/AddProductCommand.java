package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.controller.commands.util.RequestProcessor;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.ProductService;
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

public class AddProductCommand implements Command {
    private final Logger logger = LogManager.getLogger();
    private final ProductService productService = new ProductServiceImpl();

    @Override
    public String execute(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession();
            List<Product> cart = getCart(session);
            int productId = RequestProcessor.getIntFromRequest(req, REQUEST_PRODUCT);
            Product product = productService.findProductById(productId);
            CartService.addProduct(cart, product);
            session.setAttribute(SESSION_CART, cart);
            Paginator.transferPageToSession(req);
        } catch (ServiceException e) {
            logger.error("Error adding products to the cart", e);
        }
        return ConfigurationManager.getProperty("page.searchRedirect");
    }

    private List<Product> getCart(HttpSession session) {
        List<Product> cart = (ArrayList<Product>) session.getAttribute(SESSION_CART);
        if (cart == null) {
            cart = new ArrayList<>();
        }
        return cart;
    }
}
