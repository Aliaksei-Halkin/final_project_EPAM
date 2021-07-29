package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.epam.flyingdutchman.util.constants.Context.SESSION_LANGUAGE;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session =request.getSession();
        String currentLanguage = (String) session.getAttribute(SESSION_LANGUAGE);
        session.invalidate();
        session.setAttribute(SESSION_LANGUAGE,currentLanguage);
        return ConfigurationManager.getProperty("page.index");
    }
}
