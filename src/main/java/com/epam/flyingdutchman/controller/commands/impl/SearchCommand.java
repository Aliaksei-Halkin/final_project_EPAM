package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_SEARCH;
import static com.epam.flyingdutchman.util.constants.Context.SESSION_SEARCH_STRING;

public class SearchCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String searchString = request.getParameter(REQUEST_SEARCH);
    if(searchString == null){
        searchString = (String) request.getSession().getAttribute(SESSION_SEARCH_STRING);
    }
    request.getSession().setAttribute(SESSION_SEARCH_STRING,searchString);
    Integer currentPage;
        return null;
    }
}
