package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.controller.commands.util.RequestProcessor;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.ProductService;
import com.epam.flyingdutchman.model.service.impl.ProductServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_PRODUCT;
import static com.epam.flyingdutchman.util.constants.Context.REQUEST_REGISTRATION_STATUS_PRODUCT;


public class DeleteProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ProductService productService = new ProductServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        int productId = RequestProcessor.getIntFromRequest(request, REQUEST_PRODUCT);
        Paginator.transferPageToSession(request);
        try {
            productService.deactivateProduct(productId);
            request.getSession().setAttribute(REQUEST_REGISTRATION_STATUS_PRODUCT,
                    MessageManager.getMessage("msg.StatusDeleted"));
        } catch (ServiceException e) {
            logger.error("The product has not been removed from the database");
            request.getSession().setAttribute(REQUEST_REGISTRATION_STATUS_PRODUCT,
                    MessageManager.getMessage("msg.StatusDeletedError"));
        }
        return ConfigurationManager.getProperty("page.productManagementRedirect");
    }
}
