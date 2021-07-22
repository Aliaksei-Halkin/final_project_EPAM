package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.impl.ProductServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.epam.flyingdutchman.controller.commands.util.Paginator.ITEMS_ON_PAGE;
import static com.epam.flyingdutchman.util.constants.Context.*;

public class SearchCommand implements Command {
    private final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        ProductServiceImpl productService = new ProductServiceImpl();
        try {
            String searchString = request.getParameter(REQUEST_SEARCH);
            if (searchString == null) {
                searchString = (String) request.getSession().getAttribute(SESSION_SEARCH_STRING);
            }
            request.getSession().setAttribute(SESSION_SEARCH_STRING, searchString);
            int currentPage = Paginator.getCurrentPage(request);
            request.setAttribute(REQUEST_PAGE, currentPage);
            int currentIndex = Paginator.countCurrentIndex(currentPage);
            request.setAttribute(REQUEST_CURRENT_INDEX, currentIndex);
            List<Product> products = productService.searchProducts(searchString, currentIndex, ITEMS_ON_PAGE);
            request.setAttribute(REQUEST_PRODUCTS, products);
            int numberOfProducts = productService.countSearchResult(searchString);
            request.setAttribute(REQUEST_NUMBER_OF_ITEMS, numberOfProducts);
            int numberOfPages = Paginator.countNumberOfPage(numberOfProducts);
            request.setAttribute(REQUEST_NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(REQUEST_PAGINATOR_COMMAND, request.getParameter(REQUEST_COMMAND));
        } catch (ServiceException e) {
            logger.error("Error getting products",e);
        }
        return ConfigurationManager.getProperty("page.search");
    }
}
