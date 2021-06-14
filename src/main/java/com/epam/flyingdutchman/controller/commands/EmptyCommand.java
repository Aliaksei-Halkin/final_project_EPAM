package com.epam.flyingdutchman.controller.commands;

import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;

public class EmptyCommand implements  Command{
    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getProperty("page.error");
    }
}
