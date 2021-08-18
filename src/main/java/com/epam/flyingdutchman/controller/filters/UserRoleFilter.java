package com.epam.flyingdutchman.controller.filters;

import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Set;

import static com.epam.flyingdutchman.util.constants.Context.*;


@WebFilter("/controller")
public class UserRoleFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ROLE_ADMIN = 1;
    private static final int ROLE_MANAGER = 2;
    private static final int ROLE_COOK = 4;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String action = req.getParameter(REQUEST_COMMAND);
        if (action != null && isRestrictedAction(action, request)) {
            LOGGER.info("not authorized user" + request.getRequestURI(), action);
            req.setAttribute(REQUEST_ERROR_CODE,
                    MessageManager.getMessage("msg.errorCode403"));
            req.setAttribute(REQUEST_ERROR_MESSAGE,
                    MessageManager.getMessage("msg.errorMessage403"));
            req.getRequestDispatcher(ConfigurationManager.getProperty("page.error"))
                    .forward(req, res);
            return;
        }
        chain.doFilter(request, res);
    }

    private boolean isRestrictedAction(String action, HttpServletRequest request) {
        return (isRestrictedResource(action, RestrictedActions.getAdminOnlyActions())
                && userIsNotInRole(request, ROLE_ADMIN))
                || (isRestrictedResource(action, RestrictedActions.getManagerOnlyActions())
                && userIsNotInRole(request, ROLE_MANAGER))
                || (isRestrictedResource(action, RestrictedActions.getManagerOnlyActions())
                && userIsNotInRole(request, ROLE_COOK));
    }

    private boolean isRestrictedResource(String action, Set<String> actions) {
        for (String restrictedAction : actions) {
            if (action.equalsIgnoreCase(restrictedAction)) {
                return true;
            }
        }
        return false;
    }

    private boolean userIsNotInRole(HttpServletRequest request, int userRole) {
        return (int) request.getSession().getAttribute(SESSION_USER_ROLE) != userRole;
    }
}


