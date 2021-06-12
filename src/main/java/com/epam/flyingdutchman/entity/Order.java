package com.epam.flyingdutchman.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {
    private long orderId;
    private String userName;
    private LocalDateTime orderDate;
    private BigDecimal orderCost;
    private boolean confirmationStatus;
    private Map<Product, Long> listOfProducts;

    public Order() {
    }

    public Order(String userName, LocalDateTime orderDate, BigDecimal orderCost, boolean confirmationStatus, Map<Product, Long> listOfProducts) {
        this.userName = userName;
        this.orderDate = orderDate;
        this.orderCost = orderCost;
        this.confirmationStatus = confirmationStatus;
        this.listOfProducts = listOfProducts;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public boolean isConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(boolean confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public Map<Product, Long> getListOfProducts() {
        return new HashMap<Product, Long>(listOfProducts);
    }

    public void setListOfProducts(Map<Product, Long> listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && confirmationStatus == order.confirmationStatus && userName.equals(order.userName) && orderDate.equals(order.orderDate) && orderCost.equals(order.orderCost) && listOfProducts.equals(order.listOfProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, orderDate, orderCost, confirmationStatus, listOfProducts);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", userName='").append(userName);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", confirmationStatus=").append(confirmationStatus);
        sb.append(", listOfProducts=").append(listOfProducts);
        sb.append('}');
        return sb.toString();
    }
}
