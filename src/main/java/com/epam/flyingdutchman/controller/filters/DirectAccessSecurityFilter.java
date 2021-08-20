package com.epam.flyingdutchman.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
/**
 * The class represents filter for access on other jsp pages
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
@WebFilter(urlPatterns = {"/jsp/*"})
public class DirectAccessSecurityFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        LOGGER.info("User tried to access resource directly ", req.getRequestURI());
        resp.sendRedirect(req.getContextPath());
    }
}
