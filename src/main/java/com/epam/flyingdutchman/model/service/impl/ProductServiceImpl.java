package com.epam.flyingdutchman.model.service.impl;

import com.epam.flyingdutchman.entity.Product;
import com.epam.flyingdutchman.exception.DaoException;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.dao.ProductDao;
import com.epam.flyingdutchman.model.dao.impl.ProductDaoImpl;
import com.epam.flyingdutchman.model.service.ProductService;
import com.epam.flyingdutchman.model.validation.ProductValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
/**
 * The service class of model layer, intended to process operations with {@code Product}.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class ProductServiceImpl implements ProductService {
    private static final int INVALID_ID = -1;
    private final ProductDao productDao = ProductDaoImpl.getInstance();

    public ProductServiceImpl() {
    }

    @Override
    public Product findProductById(int productId) throws ServiceException {
        Optional<Product> product;
        try {
            product = productDao.findProductById(productId);
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
    public int countSearchResultProducts(String searchString) throws ServiceException {
        try {
            return productDao.countSearchResults(searchString);
        } catch (DaoException e) {
            throw new ServiceException("Error counting information of products searchProducts", e);
        }
    }

    @Override
    public List<Product> findAllProducts(int currentIndex, int itemsOnPage) throws ServiceException {
        try {
            return productDao.findAllProducts(currentIndex, itemsOnPage);
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
    public void deactivateProduct(int productId) throws ServiceException {
        Optional<Product> product;
        Product productWithId;
        try {
            product = productDao.findProductById(productId);
            if (product.isEmpty()) {
                throw new ServiceException("error while find information about the product by id");
            }
            productWithId = product.get();
            productWithId.setActive(false);
            productDao.update(productWithId);
        } catch (DaoException e) {
            throw new ServiceException("Error deactivating the product by ID", e);
        }
    }

    @Override
    public void updateProduct(Product product) throws ServiceException {
        boolean update = false;
        try {
            if (isValidDataOfNewProduct(product)) {
                update = productDao.update(product);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error updating information searchProducts", e);
        }
    }

    @Override
    public int createProduct(Product product) throws ServiceException {
        int idProduct = INVALID_ID;
        try {
            if (isValidDataOfNewProduct(product)) {
                idProduct = productDao.saveProduct(product);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error creating a new product", e);
        }
        return idProduct;
    }

    private boolean isValidDataOfNewProduct(Product product) {
        String productName = product.getName();
        BigDecimal cost = product.getCost();
        String imagePath = product.getProductImgPath();
        ProductValidator productValidator = new ProductValidator();
        boolean resultValidation = false;
        boolean isValidCost = productValidator.isValidCost(cost);
        boolean isValidProductName = productValidator.isValidProductName(productName);
        boolean isValidImagePath = productValidator.isValidImage(imagePath);
        if (isValidCost && isValidProductName && isValidImagePath) {
            resultValidation = true;
        }
        return resultValidation;
    }
}
