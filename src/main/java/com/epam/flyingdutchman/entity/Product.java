package com.epam.flyingdutchman.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private long productId;
    private long name;
    private String imagePath;
    private BigDecimal cost;
    private String description;
    private boolean active;

    public Product() {
    }

    public Product(long name, String imagePath, BigDecimal cost, String description, boolean active) {
        this.name = name;
        this.imagePath = imagePath;
        this.cost = cost;
        this.description = description;
        this.active = active;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name == product.name && active == product.active && imagePath.equals(product.imagePath) && cost.equals(product.cost) && description.equals(product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, imagePath, cost, description, active);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Product{");
        sb.append(", name=").append(name);
        sb.append(", imagePath='").append(imagePath);
        sb.append(", cost=").append(cost);
        sb.append(", description='").append(description);
        sb.append(", active=").append(active);
       return sb.toString();
    }
}
