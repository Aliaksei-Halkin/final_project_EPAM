package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.controller.commands.util.RequestProcessor;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.ProductService;
import com.epam.flyingdutchman.model.service.impl.ProductServiceImpl;
import com.epam.flyingdutchman.model.validation.ProductValidator;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static com.epam.flyingdutchman.util.constants.Context.*;
import static com.epam.flyingdutchman.util.constants.DatabaseColumn.PRODUCTS_DESCRIPTION;
import static com.epam.flyingdutchman.util.constants.DatabaseColumn.PRODUCTS_IMAGE_PATH;

public class AddNewProductCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ProductService productService = new ProductServiceImpl();
    private final ProductValidator productValidator = new ProductValidator();
    private static final String FOLDER_IMAGES = "images/";

    @Override
    public String execute(HttpServletRequest request) {
        Product product = new Product();
        Paginator.transferPageToSession(request);
        String productName = request.getParameter(REQUEST_PRODUCT_NAME);
        BigDecimal cost = RequestProcessor.getBigDecimalFromRequest(request, REQUEST_COST);
        String description = request.getParameter(PRODUCTS_DESCRIPTION);
        String imagePath = request.getParameter(PRODUCTS_IMAGE_PATH);
        if (isValidData(productName, cost)) {
            product.setName(productName);
            if (productValidator.isValidImage(imagePath)) {
                product.setProductImgPath(FOLDER_IMAGES + imagePath);
            }
            product.setCost(cost);
            product.setDescription(description);
            product.setActive(true);
            try {
                productService.createProduct(product);
                request.setAttribute(REQUEST_REGISTRATION_STATUS_PRODUCT,
                        MessageManager.getMessage("msg.registrationStatusProduct"));
            } catch (ServiceException e) {
                LOGGER.error("The product didn't add to database", e);
            }
        }
        return ConfigurationManager.getProperty("page.productManagementRedirect");
    }

    private boolean isValidData(String productName, BigDecimal cost) {
        boolean resultValidation = false;
        boolean isValidCost = productValidator.isValidCost(cost);
        boolean isValidProductName = productValidator.isValidProductName(productName);
        if (isValidCost && isValidProductName) {
            resultValidation = true;
        }
        return resultValidation;
    }
}
