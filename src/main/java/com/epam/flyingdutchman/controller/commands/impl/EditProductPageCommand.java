package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.Paginator;
import com.epam.flyingdutchman.controller.commands.util.RequestProcessor;
import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.ProductService;
import com.epam.flyingdutchman.model.service.impl.ProductServiceImpl;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_PAGE;
import static com.epam.flyingdutchman.util.constants.Context.REQUEST_PRODUCT;
/**
 * The class represents command to go to the page editProduct
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class EditProductPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ProductService productService = new ProductServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        int productId = RequestProcessor.getIntFromRequest(request, REQUEST_PRODUCT);
        Integer currentPage = Paginator.getCurrentPage(request);
        request.setAttribute(REQUEST_PAGE, currentPage);
        try {
            Product product = productService.findProductById(productId);
            request.setAttribute(REQUEST_PRODUCT, product);
        } catch (ServiceException e) {
            logger.error("Error searching the product by ID", e);
        }
        return ConfigurationManager.getProperty("page.editProduct");
    }
}
