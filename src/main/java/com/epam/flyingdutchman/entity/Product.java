package com.epam.flyingdutchman.entity;

import java.math.BigDecimal;
import java.util.Objects;
/**
 * The entity class represents a product.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class Product extends Entity {
    /**
     * The name of the product
     */
    private String name;
    /**
     * The path of image
     */
    private String productImgPath;
    /**
     * The cost of the product
     */
    private BigDecimal cost;
    /**
     * The description of the product to provide common data about it and its general characteristics.
     */
    private String description;
    /**
     * Actual status of the product. {@code True} stands for actual products that are provided by
     * supplier, {@code false} stands for products that have been already outdated and no longer
     * supplied.
     */
    private boolean active;

    /**
     * Standard no args constructor
     */
    public Product() {
    }

    /**
     * Constructor with all parameters, used to create instance of {@code Product}
     *
     * @param name        {@code String} represents the name of product
     * @param imagePath   {@code String}represents the path where saved image
     * @param cost        {@code BigDecimal} represents the cost of product
     * @param description {@code String} represents the description
     * @param active      {@code boolean} represents status of product
     */
    public Product(String name, String imagePath, BigDecimal cost, String description, boolean active) {
        this.name = name;
        this.productImgPath = imagePath;
        this.cost = cost;
        this.description = description;
        this.active = active;
    }

    /**
     * Standard getter method to access private class member.
     *
     * @return {@code String} name that represents trade name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Standard setter method to access private class member.
     *
     * @param name {@code String} represents trade name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Standard getter method to access private class member.
     *
     * @return {@code String} productImgPath that represents path of the image of product at data
     * storage.
     */
    public String getProductImgPath() {
        return productImgPath;
    }

    /**
     * Standard setter method to access private class member.
     *
     * @param productImgPath {@code String} represents path of the image of product at data
     *                       storage.
     */
    public void setProductImgPath(String productImgPath) {
        this.productImgPath = productImgPath;
    }

    /**
     * Standard getter method to access private class member.
     *
     * @return cost that represents {@code BigDecimal} value of the product's price.
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Standard setter method to access private class member.
     *
     * @param cost {@code BigDecimal} value represents cost of the product.
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Standard getter method to access private class member.
     *
     * @return {@code String} description of the product to provide common data about it and its
     * general characteristics.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Standard setter method to access private class member.
     *
     * @param description {@code String} description of the product to provide common data about it
     *                    and its general characteristics.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Standard getter method to access private class member.
     *
     * @return {@code boolean} active that represents actual status of the product. {@code True}
     * stands for actual products that are provided by supplier, {@code false} stands for products
     * that have been already outdated and no longer supplied.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Standard setter method to access private class member.
     *
     * @param active {@code boolean} represents actual status of the product. {@code True} stands
     *               for actual products that are provided by supplier, {@code false} stands for
     *               products that have been already outdated and no longer supplied.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Implementation of the equals method. Compare this instance of product to another
     * object.
     *
     * @param o {@code Object} to be compared with this {@code Product}
     * @return {@code true} is object to compare is instance of {@code Product} and it has the same
     * value of all class members
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return name.equals(product.name)
                && productImgPath.equals(product.productImgPath)
                && cost.equals(product.cost)
                && description.equals(product.description);
    }

    /**
     * Implementation of the hashCode() method. Uses method hash() of the {@code Objects} class get
     * the hash value of the class members.
     *
     * @return {@code int} value of the hash value fo the all class members
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, productImgPath, cost, description, active);
    }

    /**
     * Standard method which represents the Product in the string value
     *
     * @return {@code String} the Product
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Product{");
        sb.append(", name=").append(name);
        sb.append(", imagePath='").append(productImgPath);
        sb.append(", cost=").append(cost);
        sb.append(", description='").append(description);
        sb.append(", active=").append(active);
        return sb.toString();
    }
}
