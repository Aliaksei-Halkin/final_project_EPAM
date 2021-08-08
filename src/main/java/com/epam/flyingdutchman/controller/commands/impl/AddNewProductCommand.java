package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.ImageProcessor;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.controller.commands.util.RequestProcessor;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.ProductService;
import com.epam.flyingdutchman.model.service.impl.ProductServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.flyingdutchman.util.constants.Context.*;
import static com.epam.flyingdutchman.util.constants.DatabaseColumn.PRODUCTS_DESCRIPTION;

public class AddNewProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ProductService productService = new ProductServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        Paginator.transferPageToSession(request);
        String productName = request.getParameter(REQUEST_PRODUCT_NAME);
        BigDecimal cost = RequestProcessor.getBigDecimalFromRequest(request, REQUEST_COST);
        String description = request.getParameter(PRODUCTS_DESCRIPTION);
        String imagePath = uploadImage(request);
        Product product = new Product(productName, imagePath, cost, description, true);
        try {
            if (productService.createProduct(product) > 0) {
                request.getSession().setAttribute(REQUEST_REGISTRATION_STATUS_PRODUCT,
                        MessageManager.getMessage("msg.registrationStatusProduct"));
            } else {
                request.getSession().setAttribute(REQUEST_REGISTRATION_STATUS_PRODUCT,
                        MessageManager.getMessage("msg.registrationStatusProductFailed"));
            }
        } catch (ServiceException e) {
            logger.error("The product didn't add to database", e);
            request.getSession().setAttribute(REQUEST_REGISTRATION_STATUS_PRODUCT,
                    MessageManager.getMessage("msg.registrationStatusProductFailed"));
        }
        return ConfigurationManager.getProperty("page.productManagementRedirect");
    }

    private String uploadImage(HttpServletRequest request) {
        try {
            String uniqueFilename = ImageProcessor.generateImageFilename();
            return ImageProcessor.uploadFile(request.getPart(REQUEST_IMAGE), uniqueFilename);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return "";
    }


}
