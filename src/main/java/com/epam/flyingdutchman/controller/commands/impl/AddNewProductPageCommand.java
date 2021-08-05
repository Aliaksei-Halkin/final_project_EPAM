package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_PAGE;

public class AddNewProductPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Integer currentPage = Paginator.getCurrentPage(request);
        request.setAttribute(REQUEST_PAGE, currentPage);
        return ConfigurationManager.getProperty("page.addNewProduct");
    }
}
