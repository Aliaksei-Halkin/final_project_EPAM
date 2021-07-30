package com.epam.flyingdutchman.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order implements Entity {
    /**
     * Unique identification numeric value to define the order.
     */
    private long orderId;
    /**
     * Name of the {@code User} which is owner of the order.
     */
    private String userName;
    /**
     * Date and time of order creation.
     */
    private LocalDateTime orderDateTime;
    /**
     * Total cost of the {@code Order}. The sum of price of all {@code Product} in the order.
     */
    private BigDecimal orderCost;
    /**
     * Status of the order.
     */
    private Status status;
    /**
     * List of products represented by {@code Map} of {@code Product} and {@code Long} that
     * represents product's number.
     */
    private Map<Product, Long> listOfProducts;

    public Order() {
    }

    /**
     * Constructor with all parameters, used to create instance of {@code Order} which already has
     * unique {@param orderId} and to retrieve information about user's order from data storage.
     *
     * @param orderId        {@code long} value of unique order identification number
     * @param userName       {@code String} represents name of order's owner
     * @param orderDateTime  {@code LocalDate} of order creation
     * @param listOfProducts {@code Map} represents list of orders and its number
     * @param orderCost      {@code BigDecimal} value of total cost of order's {@code Product}
     * @param status         {@code Enum} value represents  a status of the order
     */
    public Order(long orderId, String userName, LocalDateTime orderDateTime, BigDecimal orderCost, Status status, Map<Product, Long> listOfProducts) {
        this.orderId = orderId;
        this.userName = userName;
        this.orderDateTime = orderDateTime;
        this.orderCost = orderCost;
        this.status = status;
        this.listOfProducts = listOfProducts;
    }

    public Order(String userName, BigDecimal orderCost, Map<Product, Long> listOfProducts) {
        this.userName = userName;
        this.orderCost = orderCost;

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

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        return orderId == order.orderId && status == order.status && userName.equals(order.userName) && orderDateTime.equals(order.orderDateTime) && orderCost.equals(order.orderCost) && listOfProducts.equals(order.listOfProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, orderDateTime, orderCost, status, listOfProducts);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", userName='").append(userName);
        sb.append(", orderDate=").append(orderDateTime);
        sb.append(", orderCost=").append(orderCost);
        sb.append(", status=").append(status);
        sb.append(", listOfProducts=").append(listOfProducts);
        sb.append('}');
        return sb.toString();
    }
}
