package com.epam.flyingdutchman.controller.commands.enums;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.impl.*;

public enum CommandEnum {
    REGISTER_USER(new RegisterUserCommand()),
    LANGUAGE(new LanguageCommand()),
    HOME(new HomePageCommand()),
    REDIRECT(new RedirectToGitHub()),
    SEARCH(new SearchCommand())
    ;

    private final Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
