package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.entity.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.epam.flyingdutchman.util.constants.Context.*;

public class SearchCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String searchString = request.getParameter(REQUEST_SEARCH);
    if(searchString == null){
        searchString = (String) request.getSession().getAttribute(SESSION_SEARCH_STRING);
    }
    request.getSession().setAttribute(SESSION_SEARCH_STRING,searchString);
    Integer currentPage = Paginator.getCurrentPage(request);
    request.setAttribute(REQUEST_PAGE,currentPage);
        int currentIndex = Paginator.countCurrentIndex(currentPage);
        request.setAttribute(REQUEST_CURRENT_INDEX, currentIndex);
        List<Product> products =ProductService.searchProducts(searchString, currentIndex, ITEMS_ON_PAGE);

        return null;
    }
}
