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
/**
 * The class represents command of login user
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("page.login");
        try {
            String username = request.getParameter(REQUEST_USERNAME);
            String password = request.getParameter(REQUEST_PASSWORD);
            User user = userService.findUserByUsername(username);
            if (!user.getActive()) {
                request.setAttribute(REQUEST_ERROR, MessageManager.getMessage("msg.userIsBlocked"));
                return page;
            }
            String encryptedPassword = PasswordEncryptor.encryptPassword(password);
            if (userService.checkIfValidUser(username, encryptedPassword)) {
                HttpSession session = request.getSession();
                session.setAttribute(SESSION_USERNAME, user.getUserName());
                session.setAttribute(SESSION_USER_ROLE, user.getUserRole());
                page = ConfigurationManager.getProperty("page.index");
            } else {
                logger.info("User tried to login using invalid credentials");
                request.setAttribute(REQUEST_ERROR, MessageManager.getMessage("msg.invalidCredentials"));
            }
        } catch (ServiceException e) {
            logger.error("Error validate the user", e);
        }
        return page;
    }
}
