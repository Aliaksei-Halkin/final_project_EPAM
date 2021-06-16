package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_LANGUAGE;
import static com.epam.flyingdutchman.util.constants.Context.SESSION_LANGUAGE;

public class LanguageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter(REQUEST_LANGUAGE);//по ключу находим язык
        request.getSession().setAttribute(SESSION_LANGUAGE, language);
        MessageManager.setLocale(language);
        return ConfigurationManager.getProperty("page.index");
    }
}
