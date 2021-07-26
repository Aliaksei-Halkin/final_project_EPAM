package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;


public class LoginPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getProperty("page.login");
    }
}
