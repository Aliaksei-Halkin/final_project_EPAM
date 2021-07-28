package com.epam.flyingdutchman.model.service;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.ServiceException;

import java.util.List;

public interface ProductService {
    Product findProductById(int productId) throws ServiceException;

    List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) throws ServiceException;

    int countSearchResultProducts(String searchString) throws ServiceException;

    List<Product> findAllProducts(int currentIndex, int itemsOnPage) throws ServiceException;

    int countProducts() throws ServiceException;

    boolean deactivateProduct(int productId) throws ServiceException;

    boolean updateProduct(Product product) throws ServiceException;

    int createProduct(Product product) throws ServiceException;
}
