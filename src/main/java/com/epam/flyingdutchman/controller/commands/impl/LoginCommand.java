package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.PasswordEncryptor;
import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.UserService;
import com.epam.flyingdutchman.model.service.impl.UserServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.flyingdutchman.util.constants.Context.*;

public class LoginCommand implements Command {
    private final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        UserServiceImpl userService = new UserServiceImpl();
        User user;
        String username = request.getParameter(REQUEST_USERNAME);
        String password = request.getParameter(REQUEST_PASSWORD);
        String encryptedPassword = PasswordEncryptor.encryptPassword(password);
        try {
            if (userService.isValidUser(username, encryptedPassword)) {
                HttpSession session = request.getSession();
                user = userService.getUserByUsername(username);
                session.setAttribute(SESSION_USERNAME, user.getUserName());
                session.setAttribute(SESSION_USER_ROLE, user.getUserRole());
                return ConfigurationManager.getProperty("page.index");
            } else {
                logger.info("User tried to login using invalid credentials");
                request.setAttribute(REQUEST_ERROR, MessageManager.getMessage("msg.invalidCredentials"));
            }
        } catch (ServiceException e) {
            logger.error("Error validate the user", e);
        }
        return  ConfigurationManager.getProperty("page.login");
    }
}