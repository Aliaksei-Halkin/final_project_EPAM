package com.epam.flyingdutchman.controller.commands.util;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public class RequestProcessor {
    public RequestProcessor() {
    }

    public static int getIntFromRequest(HttpServletRequest req, String paramName) {
        int number = 0;
        try {
            number = Integer.parseInt(req.getParameter(paramName));
        } catch (NumberFormatException e) {
            //  LOGGER.error(e.getMessage(), e);
        }
        return number;
    }

    public static BigDecimal getBigDecimalFromRequest(HttpServletRequest req, String paramName) {
        BigDecimal number = BigDecimal.ZERO;
        try {
            number = BigDecimal.valueOf(Double.parseDouble(req.getParameter(paramName)));
        } catch (NumberFormatException e) {
            //  LOGGER.error(e.getMessage(), e);
        }
        return number;
    }
}
