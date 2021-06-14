package com.epam.flyingdutchman.controller.commands.enums;

import com.epam.flyingdutchman.controller.commands.Command;
import jakarta.servlet.http.HttpServletRequest;

public enum CommandEnum {


    private final Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
