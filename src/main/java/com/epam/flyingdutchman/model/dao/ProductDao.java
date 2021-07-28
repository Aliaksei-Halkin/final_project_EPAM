package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    int saveProduct(Product product) throws DaoException;

    List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) throws DaoException;

    int countSearchResults(String searchString) throws DaoException;

    List<Product> findAllProducts(int currentIndex, int itemsOnPage) throws DaoException;

    int countProducts() throws DaoException;

    Optional<Product> findProductById(int productId) throws DaoException;

    boolean updateProduct(Product product) throws DaoException;
}
