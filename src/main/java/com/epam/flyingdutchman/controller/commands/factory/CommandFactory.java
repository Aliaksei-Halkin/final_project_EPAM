package com.epam.flyingdutchman.controller.commands.factory;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.enums.CommandEnum;
import com.epam.flyingdutchman.controller.commands.impl.EmptyCommand;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.flyingdutchman.util.constants.Context.*;

/**
 * The class represents command provider which define command from request.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class CommandFactory {
    public CommandFactory() {
    }

    /**
     * Define command.
     *
     * @param req the http request
     * @return the command
     */
    public static Command defineCommand(HttpServletRequest req) {
        String action = req.getParameter(REQUEST_COMMAND);
        Command command = new EmptyCommand();
        if (action == null || action.isEmpty()) {
            sendPageNotFound(req);
            return command;
        }
        try {
            command = CommandEnum.valueOf(action.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            sendPageNotFound(req);
        }
        return command;
    }

    /**
     * The method set message 404 if action is not present
     * @param req the http request
     */
    private static void sendPageNotFound(HttpServletRequest req) {
        req.setAttribute(REQUEST_ERROR_CODE,
                MessageManager.getMessage("msg.errorCode404"));
        req.setAttribute(REQUEST_ERROR_MESSAGE,
                MessageManager.getMessage("msg.errorMessage404"));
    }
}
