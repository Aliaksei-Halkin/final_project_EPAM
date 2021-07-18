package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.DaoException;

import java.util.List;

public interface ProductDao {
    int save(Product product) throws DaoException;
    List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) throws DaoException;
    int countSearchResults(String searchString) throws DaoException;
    List<Product> getAll(int currentIndex, int itemsOnPage) throws DaoException;
    int countProducts() throws DaoException;
    Product getById(int productId) throws DaoException;
    boolean update(Product product) throws DaoException;
}
