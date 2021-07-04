package com.epam.flyingdutchman.model.dao;

import com.epam.flyingdutchman.entity.Product;

import java.util.List;

public interface ProductDao {
    int save(Product product);
    List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage);
    int countSearchResults(String searchString);
    List<Product> getAll(int currentIndex, int itemsOnPage);
    int countProducts();
    Product getById(int productId);
    boolean update(Product product);
}
