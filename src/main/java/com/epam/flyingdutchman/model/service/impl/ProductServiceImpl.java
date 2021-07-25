package com.epam.flyingdutchman.model.service.impl;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.DaoException;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.dao.ProductDao;
import com.epam.flyingdutchman.model.dao.impl.ProductDaoImpl;
import com.epam.flyingdutchman.model.service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao = ProductDaoImpl.getInstance();

    public ProductServiceImpl() {
    }

    @Override
    public Product getById(int productId) throws ServiceException {
        Optional<Product> product;
        try {
            product = productDao.getById(productId);
            if (product.isEmpty()) {
                throw new ServiceException("error while find information about the product by id");
            }
        } catch (DaoException e) {
            throw new ServiceException("error while find information about the product by id", e);
        }
        return product.get();
    }


    @Override
    public List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return productDao.searchProducts(searchString, currentIndex, itemsOnPage);
        } catch (DaoException e) {
            throw new ServiceException("Error finding information of searchProducts", e);
        }
    }

    @Override
    public int countSearchResult(String searchString) throws ServiceException {
        try {
            return productDao.countSearchResults(searchString);
        } catch (DaoException e) {
            throw new ServiceException("Error counting information of products searchProducts", e);
        }
    }

    @Override
    public List<Product> getAll(int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return productDao.getAll(currentIndex, itemsOnPage);
        } catch (DaoException e) {
            throw new ServiceException("Error getting all products", e);
        }
    }

    @Override
    public int countProducts() throws ServiceException {
        try {
            return productDao.countProducts();
        } catch (DaoException e) {
            throw new ServiceException("Error counting products", e);
        }
    }

    @Override
    public boolean deactivateProduct(int productId) throws ServiceException {
        Optional<Product> product;
        Product productWithId;
        try {
            product = productDao.getById(productId);
            if (product.isEmpty()) {
                throw new ServiceException("error while find information about the product by id");
            }
            productWithId = product.get();
            productWithId.setActive(false);
            productDao.update(productWithId);
        } catch (DaoException e) {
            throw new ServiceException("Error deactivating the product by ID", e);
        }
        return true;
    }

    @Override
    public boolean updateProduct(Product product) throws ServiceException {
        try {
            return productDao.update(product);
        } catch (DaoException e) {
            throw new ServiceException("Error updating information searchProducts", e);
        }
    }

    @Override
    public int createProduct(Product product) throws ServiceException {
        try {
            return productDao.save(product);
        } catch (DaoException e) {
            throw new ServiceException("Error creating a new product");
        }
    }
}
