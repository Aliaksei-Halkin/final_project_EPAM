package com.epam.flyingdutchman.controller.commands;

import jakarta.servlet.http.HttpServletRequest;
/**
 * The interface Command which define functionality of commands.
 */
public interface Command {
    String execute(HttpServletRequest request);
}
