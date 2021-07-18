package com.epam.flyingdutchman.model.service;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.DaoException;
import com.epam.flyingdutchman.exception.ServiceException;

import java.util.List;

public interface ProductService {
    Product getById(int productId) throws DaoException, ServiceException;

    List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) throws ServiceException;

    int countSearchResult(String searchString) throws DaoException, ServiceException;

    List<Product> getAll(int currentIndex, int itemsOnPage) throws ServiceException;

    int countProducts() throws ServiceException;

    boolean deactivateProduct(int productId) throws ServiceException;

    boolean updateProduct(Product product) throws ServiceException;

    int createProduct(Product product) throws ServiceException;
}
