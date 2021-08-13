package com.epam.flyingdutchman.controller;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.factory.CommandFactory;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_ERROR_CODE;
import static com.epam.flyingdutchman.util.constants.Context.REQUEST_ERROR_MESSAGE;

@WebServlet("/controller")
@MultipartConfig(fileSizeThreshold = Controller.Mb1,
        maxFileSize = Controller.Mb10,
        maxRequestSize = Controller.Mb100)
public class Controller extends HttpServlet {
    static final int Mb1 = 1024 * 1024;
    static final int Mb10 = 1024 * 1024 * 10;
    static final int Mb100 = 1024 * 1024 * 100;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = CommandFactory.defineCommand(req);
        String page = command.execute(req);
        if (page != null) {
            req.getRequestDispatcher(page).forward(req, resp);
        } else {
            req.setAttribute(REQUEST_ERROR_CODE,
                    MessageManager.getMessage("msg.errorCode404"));
            req.setAttribute(REQUEST_ERROR_MESSAGE,
                    MessageManager.getMessage("msg.errorMessage404"));
            req.getRequestDispatcher(ConfigurationManager.getProperty("page.error"))
                    .forward(req, resp);
        }
    }
}

