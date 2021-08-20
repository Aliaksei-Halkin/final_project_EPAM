package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_LANGUAGE;
import static com.epam.flyingdutchman.util.constants.Context.SESSION_LANGUAGE;
/**
 * The class represents command of change language
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class LanguageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter(REQUEST_LANGUAGE);
        request.getSession().setAttribute(SESSION_LANGUAGE, language);
        MessageManager.setLocale(language);
        return ConfigurationManager.getProperty("page.index");
    }
}
