package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.UserService;
import com.epam.flyingdutchman.model.service.impl.UserServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.epam.flyingdutchman.controller.commands.util.Paginator.ITEMS_ON_PAGE;
import static com.epam.flyingdutchman.util.constants.Context.*;

public class UserManagementCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            Integer currentPage = Paginator.getCurrentPage(request);
            int currentIndex = Paginator.countCurrentIndex(currentPage);
            List<User> users = userService.findAll(currentIndex, ITEMS_ON_PAGE);
            int numberOfUsers = userService.countUsers();
            int numberOfPages = Paginator.countNumberOfPages(numberOfUsers);
            request.setAttribute(REQUEST_PAGE, currentPage);
            request.setAttribute(REQUEST_CURRENT_INDEX, currentIndex);
            request.setAttribute(REQUEST_USERS, users);
            request.setAttribute(REQUEST_NUMBER_OF_ITEMS, numberOfUsers);
            request.setAttribute(REQUEST_NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(REQUEST_PAGINATOR_COMMAND, request.getParameter(REQUEST_COMMAND));
        } catch (ServiceException e) {
            logger.error("Error managing of users", e);
        }
        return ConfigurationManager.getProperty("page.userManagement");
    }
}
