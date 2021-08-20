package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_REGISTRATION_STATUS;
import static com.epam.flyingdutchman.util.constants.Context.SESSION_REGISTRATION_STATUS;
/**
 * The class represents command to go to the registration page
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class RegisterUserPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String registerStatus = request.getParameter(REQUEST_REGISTRATION_STATUS);
        if (registerStatus == null) {
            registerStatus = "";
        }
        request.getSession().setAttribute(SESSION_REGISTRATION_STATUS, registerStatus);
        return ConfigurationManager.getProperty("page.register");
    }
}
