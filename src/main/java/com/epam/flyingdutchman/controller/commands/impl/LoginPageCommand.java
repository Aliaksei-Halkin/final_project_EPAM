package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The class represents command to go to the login page
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class LoginPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getProperty("page.login");
    }
}
