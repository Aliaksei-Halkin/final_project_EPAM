package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.model.service.ProductService;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.epam.flyingdutchman.controller.commands.util.Paginator.ITEMS_ON_PAGE;
import static com.epam.flyingdutchman.util.constants.Context.*;

public class SearchCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        ProductService productService = new ProductService();
        String searchString = request.getParameter(REQUEST_SEARCH);
        if (searchString == null) {
            searchString = (String) request.getSession().getAttribute(SESSION_SEARCH_STRING);
        }
        request.getSession().setAttribute(SESSION_SEARCH_STRING, searchString);
        Integer currentPage = Paginator.getCurrentPage(request);
        request.setAttribute(REQUEST_PAGE, currentPage);
        int currentIndex = Paginator.countCurrentIndex(currentPage);
        request.setAttribute(REQUEST_CURRENT_INDEX, currentIndex);
        List<Product> products = productService.searchProducts(searchString, currentIndex, ITEMS_ON_PAGE);
        System.out.println(products);
        request.setAttribute(REQUEST_PRODUCTS, products);
        int numberOfProducts = productService.countSearchResult(searchString);
        request.setAttribute(REQUEST_NUMBER_OF_ITEMS, numberOfProducts);
        int numberOfPages = Paginator.countNumberOfPage(numberOfProducts);
        request.setAttribute(REQUEST_NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(REQUEST_PAGINATOR_COMMAND, request.getParameter(REQUEST_COMMAND));
        return ConfigurationManager.getProperty("page.search");
    }
}
