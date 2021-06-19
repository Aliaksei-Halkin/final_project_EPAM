package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_SEARCH;

public class SearchCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String searchString = request.getParameter(REQUEST_SEARCH);

        return null;
    }
}
