package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
/**
 * The class represents command to go to the github
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class RedirectToGitHub implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getProperty("page.github");
    }
}
