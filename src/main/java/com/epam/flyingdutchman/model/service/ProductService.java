package com.epam.flyingdutchman.model.service;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.model.dao.ProductDao;
import com.epam.flyingdutchman.model.dao.impl.ProductDaoImpl;

import java.util.List;

public class ProductService {
    private static ProductDao productDao = ProductDaoImpl.getInstance();

    public ProductService() {
    }

    public Product getById(int productId) {
        return productDao.getById(productId);
    }

    public List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) {
        return productDao.searchProducts(searchString, currentIndex, itemsOnPage);
    }

    public int countSearchResult(String searchString) {
        return productDao.countSearchResults(searchString);
    }

    public List<Product> getAll(int currentIndex, int itemsOnPage) {
        return productDao.getAll(currentIndex, itemsOnPage);
    }

    public int countProducts() {
        return productDao.countProducts();
    }

    public boolean deactivateProduct(int productId) {
        Product product = productDao.getById(productId);
        product.setActive(false);
        return productDao.update(product);
    }

    public boolean updateProduct(Product product) {
        return productDao.update(product);
    }

    public int createProduct(Product product) {
        return productDao.save(product);
    }
}
