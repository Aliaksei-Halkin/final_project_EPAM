package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.UserService;
import com.epam.flyingdutchman.model.service.impl.UserServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.flyingdutchman.util.constants.Context.*;

public class UserDeleteCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter(REQUEST_USERNAME);
        try {
            userService.deleteUser(username);
            Paginator.transferPageToSession(request);
            request.getSession().setAttribute(STATUS_USER_OPERATION,
                    MessageManager.getMessage("msg.statusUserOperationDelete"));
        } catch (ServiceException e) {
            logger.error("Error while deleting user " + username, e);
            request.getSession().setAttribute(STATUS_USER_OPERATION,
                    MessageManager.getMessage("msg.statusUserOperationDeleteError"));
        }
        return ConfigurationManager.getProperty("page.userManagementRedirect");
    }
}
