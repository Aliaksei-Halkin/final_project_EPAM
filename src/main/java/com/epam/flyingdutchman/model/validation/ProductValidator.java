package com.epam.flyingdutchman.model.validation;

import java.math.BigDecimal;

public class ProductValidator {
    private static final String PRODUCT_REGEX = "[a-zA-Zа-яА-Я0-9_\\s\\-().,]{4,50}";
    private static final String JPG = ".jpg";

    public boolean isValidProductName(String productName) {
        return productName.matches(PRODUCT_REGEX);
    }

    public boolean isValidImage(String path) {
        if (path == null) {
            return false;
        }
        return path.toLowerCase().endsWith(JPG);
    }

    public boolean isValidCost(BigDecimal cost) {
        return cost.compareTo(BigDecimal.ZERO) > 0;
    }
}
