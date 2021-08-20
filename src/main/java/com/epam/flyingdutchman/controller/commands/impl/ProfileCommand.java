package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.UserService;
import com.epam.flyingdutchman.model.service.impl.UserServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_USER;
import static com.epam.flyingdutchman.util.constants.Context.SESSION_USERNAME;

/**
 * The class represents command to go to the profile page
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class ProfileCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute(SESSION_USERNAME);
        try {
            User user = userService.findUserByUsername(username);
            request.setAttribute(REQUEST_USER, user);
        } catch (ServiceException e) {
            LOGGER.error("Error when searching for user data", e);
        }
        return ConfigurationManager.getProperty("page.profile");
    }
}
