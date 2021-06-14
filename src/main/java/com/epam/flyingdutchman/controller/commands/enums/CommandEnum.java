package com.epam.flyingdutchman.controller.commands.enums;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.impl.RegisterUserCommand;

public enum CommandEnum {
    REGISTER_USER(new RegisterUserCommand());

    private final Command command;

    CommandEnum(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}
