package com.epam.flyingdutchman.model.service;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.ServiceException;

import java.util.List;
/**
 * The interface represents {@code Product} service
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public interface ProductService {
    Product findProductById(int productId) throws ServiceException;

    List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) throws ServiceException;

    int countSearchResultProducts(String searchString) throws ServiceException;

    List<Product> findAllProducts(int currentIndex, int itemsOnPage) throws ServiceException;

    int countProducts() throws ServiceException;

    void deactivateProduct(int productId) throws ServiceException;

    void updateProduct(Product product) throws ServiceException;

    int createProduct(Product product) throws ServiceException;
}
