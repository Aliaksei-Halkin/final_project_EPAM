package com.epam.flyingdutchman.model.dao.impl;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.model.dao.ProductDao;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public int save(Product product) {
        return 0;
    }

    @Override
    public List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) {
        return null;
    }

    @Override
    public int countSearchResults(String searchString) {
        return 0;
    }

    @Override
    public List<Product> getAll(int currentIndex, int itemsOnPage) {
        return null;
    }

    @Override
    public int countProducts() {
        return 0;
    }

    @Override
    public Product getById(int productId) {
        return null;
    }

    @Override
    public boolean update(Product product) {
        return false;
    }
}
