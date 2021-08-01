package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.ProductService;
import com.epam.flyingdutchman.model.service.impl.ProductServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.epam.flyingdutchman.controller.commands.util.Paginator.ITEMS_ON_PAGE;
import static com.epam.flyingdutchman.util.constants.Context.*;

public class ProductManagementCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger();
    private final static int INVALID_VALUE = -1;
    private final ProductService productService = new ProductServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        List<Product> products = new ArrayList<>();
        Integer currentPage = Paginator.getCurrentPage(request);
        int currentIndex = Paginator.countCurrentIndex(currentPage);
        int numberOfProducts = INVALID_VALUE;
        int numberOfPages = INVALID_VALUE;
        try {
            products = productService.findAllProducts(currentIndex, ITEMS_ON_PAGE);
            numberOfProducts = productService.countProducts();
            numberOfPages = Paginator.countNumberOfPages(numberOfProducts);
        } catch (ServiceException e) {
            LOGGER.error("Error while finding products", e);
        }
        request.setAttribute(REQUEST_PAGE, currentPage);
        request.setAttribute(REQUEST_CURRENT_INDEX, currentIndex);
        request.setAttribute(REQUEST_PRODUCTS, products);
        request.setAttribute(REQUEST_NUMBER_OF_ITEMS, numberOfProducts);
        request.setAttribute(REQUEST_NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(REQUEST_PAGINATOR_COMMAND, request.getParameter(REQUEST_COMMAND));
        return ConfigurationManager.getProperty("page.productManagement");
    }
}
