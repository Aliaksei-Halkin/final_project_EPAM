package com.epam.flyingdutchman.controller.filters;

import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_ERROR;
import static com.epam.flyingdutchman.util.constants.Context.SESSION_USERNAME;

/**
 *
 */
@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String action = req.getParameter("command");
        if (isRestrictedAction(action, request)) {
            LOGGER.info("Unauthorized user tried to reach restricted resource " +
                    request.getRequestURI(), action);
            req.setAttribute(REQUEST_ERROR, "You are not authorized. Please, login");
            req.getRequestDispatcher(
                    ConfigurationManager.getProperty("page.login")).forward(request, res);
            return;
        }
        chain.doFilter(request, res);
    }

    private boolean isRestrictedAction(String action, HttpServletRequest request) {
        return action != null
                && isRestrictedResource(action)
                && userIsNotAuthorized(request);
    }

    private boolean isRestrictedResource(String action) {
        for (String restrictedAction : RestrictedActions.getAuthenticatedUserOnlyActions()) {
            if (action.equalsIgnoreCase(restrictedAction)) {
                return true;
            }
        }
        return false;
    }

    private boolean userIsNotAuthorized(HttpServletRequest request) {
        return request.getSession().getAttribute(SESSION_USERNAME) == null;
    }
}
