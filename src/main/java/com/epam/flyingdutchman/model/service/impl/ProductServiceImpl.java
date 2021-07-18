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
            product = Optional.ofNullable(productDao.getById(productId));
            if (product.isEmpty()) {
                throw new ServiceException("error while find information about product by id");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return product.get();
    }


    @Override
    public List<Product> searchProducts(String searchString, int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return productDao.searchProducts(searchString, currentIndex, itemsOnPage);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int countSearchResult(String searchString) throws ServiceException {
        try {
            return productDao.countSearchResults(searchString);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Product> getAll(int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return productDao.getAll(currentIndex, itemsOnPage);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int countProducts() throws ServiceException {
        try {
            return productDao.countProducts();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean deactivateProduct(int productId) throws ServiceException {
        try {
            Product product = productDao.getById(productId);
            product.setActive(false);
            return productDao.update(product);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean updateProduct(Product product) throws ServiceException {
        try {
            return productDao.update(product);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int createProduct(Product product) throws ServiceException {
        try {
            return productDao.save(product);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
