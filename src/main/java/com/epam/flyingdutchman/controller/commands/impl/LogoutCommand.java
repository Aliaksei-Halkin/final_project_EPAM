package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static com.epam.flyingdutchman.util.constants.Context.SESSION_LANGUAGE;
/**
 * The class represents command of logout from the personal account
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<Object> optional = Optional.ofNullable(session.getAttribute(SESSION_LANGUAGE));
        session.invalidate();
        if (optional.isEmpty()) {
            request.getSession().setAttribute(SESSION_LANGUAGE, "ru");
        } else {
            request.getSession().setAttribute(SESSION_LANGUAGE, optional.get());
        }
        return ConfigurationManager.getProperty("page.index");
    }
}
