package com.epam.flyingdutchman.controller.commands.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
/**
 * The class represents actions for getting values(int,BigDecimal) from request
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class RequestProcessor {
    private static final Logger LOGGER = LogManager.getLogger();

    public RequestProcessor() {
    }

    public static int getIntFromRequest(HttpServletRequest req, String paramName) {
        int number = 0;
        try {
            if (req.getParameter(paramName) == null || req.getParameter(paramName).equals("")) {
                return number;
            }
            number = Integer.parseInt(req.getParameter(paramName));
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return number;
    }

    public static BigDecimal getBigDecimalFromRequest(HttpServletRequest req, String paramName) {
        BigDecimal number = BigDecimal.ZERO;
        try {
            number = BigDecimal.valueOf(Double.parseDouble(req.getParameter(paramName)));
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return number;
    }
}
