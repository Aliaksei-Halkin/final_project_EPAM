package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;

public class MakeOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // TODO: 7/22/2021 will add autorization and this realization 
        return ConfigurationManager.getProperty("page.cart");
    }
}
