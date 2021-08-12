package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.controller.commands.util.RequestProcessor;
import com.epam.flyingdutchman.entity.Status;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.OrderService;
import com.epam.flyingdutchman.model.service.impl.OrderServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.flyingdutchman.util.constants.Context.*;

public class ChangeOrderStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        int orderId = RequestProcessor.getIntFromRequest(request, REQUEST_ORDER);
        int page = RequestProcessor.getIntFromRequest(request, REQUEST_PAGE);
        String status = request.getParameter(REQUEST_STATUS);
        Paginator.transferPageToSession(request);
        try {
            orderService.changeOrderStatus(orderId, Status.valueOf(status));
        } catch (ServiceException e) {
            logger.error("Error changing status of order " + orderId, e);
        }
        return ConfigurationManager.getProperty("page.orderManagementRedirect");
    }
}
