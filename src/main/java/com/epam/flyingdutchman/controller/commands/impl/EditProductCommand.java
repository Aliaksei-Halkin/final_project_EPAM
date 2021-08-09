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
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static com.epam.flyingdutchman.util.constants.Context.*;

public class EditProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ProductService productService = new ProductServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            Product updatingProduct;
            String productName = request.getParameter(REQUEST_PRODUCT_NAME);
            BigDecimal cost = RequestProcessor.getBigDecimalFromRequest(request, REQUEST_COST);
            String description = request.getParameter(REQUEST_DESCRIPTION);
            int productId = RequestProcessor.getIntFromRequest(request, REQUEST_PRODUCT);
            updatingProduct = productService.findProductById(productId);
            checkNewField(request, updatingProduct, productName, cost, description);
            productService.updateProduct(updatingProduct);
            Paginator.transferPageToSession(request);
            request.getSession().setAttribute(REQUEST_REGISTRATION_STATUS_PRODUCT,
                    MessageManager.getMessage("msg.StatusUpdate"));
        } catch (ServiceException e) {
            logger.error("Error updating the product ", e);
            request.getSession().setAttribute(REQUEST_REGISTRATION_STATUS_PRODUCT,
                    MessageManager.getMessage("msg.StatusUpdateError"));
        }
        return ConfigurationManager.getProperty("page.productManagementRedirect");
    }

    private void checkNewField(HttpServletRequest request, Product updatingProduct, String productName, BigDecimal cost, String description) {
        String imageFileName;
        if (request.getParameter(REQUEST_CHANGE_IMAGE) != null) {
            imageFileName = ImageProcessor.uploadImage(request);
            updatingProduct.setProductImgPath(imageFileName);
        }
        if (productName != null) {
            updatingProduct.setName(productName);
        }
        if (cost != null) {
            updatingProduct.setCost(cost);
        }
        if (description != null) {
            updatingProduct.setDescription(description);
        }
    }
}
