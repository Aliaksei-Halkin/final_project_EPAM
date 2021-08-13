package com.epam.flyingdutchman.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Product extends Entity {
    private String name;
    private String productImgPath;
    private BigDecimal cost;
    private String description;
    private boolean active;

    public Product() {
    }

    public Product(String name, String imagePath, BigDecimal cost, String description, boolean active) {
        this.name = name;
        this.productImgPath = imagePath;
        this.cost = cost;
        this.description = description;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductImgPath() {
        return productImgPath;
    }

    public void setProductImgPath(String productImgPath) {
        this.productImgPath = productImgPath;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

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
