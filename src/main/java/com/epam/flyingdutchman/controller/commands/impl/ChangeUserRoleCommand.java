package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.controller.commands.util.RequestProcessor;
import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.UserService;
import com.epam.flyingdutchman.model.service.impl.UserServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_NEW_ROLE;
import static com.epam.flyingdutchman.util.constants.Context.REQUEST_USER;

public class ChangeUserRoleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            String username = request.getParameter(REQUEST_USER);
            int newRole = RequestProcessor.getIntFromRequest(request, REQUEST_NEW_ROLE);
            User user = userService.findUserByUsername(username);
            userService.changeRole(user, newRole);
            Paginator.transferPageToSession(request);
        } catch (ServiceException e) {
            logger.error("Error changing role of user",e);
        }
        return ConfigurationManager.getProperty("page.userManagementRedirect");
    }
}
