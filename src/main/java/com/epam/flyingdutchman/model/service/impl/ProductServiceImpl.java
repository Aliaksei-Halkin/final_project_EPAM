package com.epam.flyingdutchman.model.service.impl;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.model.dao.ProductDao;
import com.epam.flyingdutchman.model.dao.impl.ProductDaoImpl;
import com.epam.flyingdutchman.model.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private static ProductDao productDao = ProductDaoImpl.getInstance();

    public ProductServiceImpl() {
    }

    @Override
    public Product getById(int productId) {
        return productDao.getById(productId);
    }

    @Override
    public List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) {
        return productDao.searchProducts(searchString, currentIndex, itemsOnPage);
    }

    @Override
    public int countSearchResult(String searchString) {
        return productDao.countSearchResults(searchString);
    }

    @Override
    public List<Product> getAll(int currentIndex, int itemsOnPage) {
        return productDao.getAll(currentIndex, itemsOnPage);
    }

    @Override
    public int countProducts() {
        return productDao.countProducts();
    }

    @Override
    public boolean deactivateProduct(int productId) {
        Product product = productDao.getById(productId);
        product.setActive(false);
        return productDao.update(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        return productDao.update(product);
    }

    @Override
    public int createProduct(Product product) {
        return productDao.save(product);
    }
}
