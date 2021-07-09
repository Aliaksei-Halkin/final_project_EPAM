package com.epam.flyingdutchman.model.service;

import com.epam.flyingdutchman.entity.Product;

import java.util.List;

public interface ProductService {
    Product getById(int productId);

    List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage);

    int countSearchResult(String searchString);

    List<Product> getAll(int currentIndex, int itemsOnPage);

    int countProducts();

    boolean deactivateProduct(int productId);

    boolean updateProduct(Product product);

    int createProduct(Product product);
}
