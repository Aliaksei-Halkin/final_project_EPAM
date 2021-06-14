package com.epam.flyingdutchman.controller;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.factory.CommandFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/controller")
public class Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
}

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
    Command command = CommandFactory.defineCommand(req);

    }

}
