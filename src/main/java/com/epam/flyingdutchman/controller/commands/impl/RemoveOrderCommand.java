package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.controller.commands.util.RequestProcessor;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.OrderService;
import com.epam.flyingdutchman.model.service.impl.OrderServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_ORDER;

public class RemoveOrderCommand implements Command {
    private final Logger logger = LogManager.getLogger();
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        int orderId = RequestProcessor.getIntFromRequest(request, REQUEST_ORDER);
        try {
            orderService.removeOrder(orderId);
        } catch (ServiceException e) {
            logger.error("Error in RemoveOrderCommand", e);
        }
        Paginator.transferPageToSession(request);
        return ConfigurationManager.getProperty("page.historyRedirect");
    }
}
