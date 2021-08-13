package com.epam.flyingdutchman.controller.commands.factory;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.impl.EmptyCommand;
import com.epam.flyingdutchman.controller.commands.enums.CommandEnum;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.flyingdutchman.util.constants.Context.*;

public class CommandFactory {
    public CommandFactory() {
    }

    public static Command defineCommand(HttpServletRequest req) {
        String action =req.getParameter(REQUEST_COMMAND);
        Command command = new EmptyCommand();
        if(action==null||action.isEmpty()){
            sendPageNotFound(req);
            return command;
        }
        try {
            command = CommandEnum.valueOf(action.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            sendPageNotFound(req);//todo maybe you'll need to do redirect to index.jsp?
        }
        return command;

    }
    private static void sendPageNotFound(HttpServletRequest req) {
        req.setAttribute(REQUEST_ERROR_CODE,
                MessageManager.getMessage("msg.errorCode404"));
        req.setAttribute(REQUEST_ERROR_MESSAGE,
                MessageManager.getMessage("msg.errorMessage404"));
    }
}