package com.epam.flyingdutchman.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The entity class represents user's order.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class Order extends Entity {

    /**
     * The name of the {@code User} which is owner of the order.
     */
    private String userName;
    /**
     * The date and time of order creation.
     */
    private LocalDateTime orderDateTime;
    /**
     * The total cost of the {@code Order}. The sum of price of all {@code Product} in the order.
     */
    private BigDecimal orderCost;
    /**
     * The status of the order.
     */
    private Status status;
    /**
     * The list of products represented by {@code Map} of {@code Product} and {@code Long} that
     * represents product's number.
     */
    private Map<Product, Long> listOfProducts;

    /**
     * The no-args constructor
     */
    public Order() {
    }

    /**
     * The constructor with all parameters, used to create instance of {@code Order} which already has
     * unique {@param orderId} and to retrieve information about user's order from data storage.
     *
     * @param orderId        {@code long} value of unique order identification number
     * @param userName       {@code String} represents name of order's owner
     * @param orderDateTime  {@code LocalDate} of order creation
     * @param listOfProducts {@code Map} represents list of orders and its number
     * @param orderCost      {@code BigDecimal} value of total cost of order's {@code Product}
     * @param status         {@code Enum} value represents  a status of the order
     */
    public Order(int orderId, String userName, LocalDateTime orderDateTime, BigDecimal orderCost, Status status, Map<Product, Long> listOfProducts) {
        super(orderId);
        this.userName = userName;
        this.orderDateTime = orderDateTime;
        this.orderCost = orderCost;
        this.status = status;
        this.listOfProducts = listOfProducts;
    }

    /**
     * The constructor with some parameters, other fields of the Order create database by default
     *
     * @param userName       {@code String} represents name of order's owner
     * @param listOfProducts {@code Map} represents list of orders and its number
     * @param orderCost      {@code BigDecimal} value of total cost of order's {@code Product}
     */
    public Order(String userName, BigDecimal orderCost, Map<Product, Long> listOfProducts) {
        this.userName = userName;
        this.orderCost = orderCost;
        this.listOfProducts = listOfProducts;
    }

    /**
     * Standard getter method to access private class member.
     *
     * @return {@code String} username represents name of order's owner
     */
    public String getUserName() {
        return userName;
    }

    /**
     * The standard setter method to access private class member.
     *
     * @param userName {@code String} represents name of order's owner
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * The standard getter method to access private class member.
     *
     * @return {@code LocalDateTime} of order creation
     */
    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    /**
     * The standard setter method to access private class member.
     *
     * @param orderDateTime {@code LocalDateTime}value of the current time and date
     */
    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    /**
     * The standard getter method to access private class member.
     *
     * @return {@code BigDecimal}value of total cost of all order's products
     */
    public BigDecimal getOrderCost() {
        return orderCost;
    }

    /**
     * The standard setter method to access private class member.
     *
     * @param orderCost {@code BigDecimal} value of total cost of order's {@code Product}
     */
    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    /**
     * The standard getter method to access private class member.
     *
     * @return {@code Status} value that represents  status of the order
     */
    public Status getStatus() {
        return status;
    }

    /**
     * The standard setter method to access private class member.
     *
     * @param status {@code boolean} value represents  status of the order
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * The getter method returns the map of {@code Product} in order to facilitate
     * process of displaying and processing of data.
     *
     * @return {@code HashMap} of  {@code Product} and its number represented by
     * {@code Long} value
     */
    public Map<Product, Long> getListOfProducts() {
        return new HashMap<Product, Long>(listOfProducts);
    }

    /**
     * The standard setter method to access private class member.
     *
     * @param listOfProducts {@code Map} represents list of orders and its number
     */
    public void setListOfProducts(Map<Product, Long> listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

    /**
     * The implementation of the equals method. Compare this instance of product to another
     * object.
     *
     * @param o {@code Object} to be compared with this {@code Order}
     * @return {@code true} is object to compare is instance of {@code Order} and it has the same
     * value of all class members
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(userName, order.userName) && Objects.equals(orderDateTime, order.orderDateTime) && Objects.equals(orderCost, order.orderCost) && status == order.status && Objects.equals(listOfProducts, order.listOfProducts);
    }

    /**
     * The implementation of the hashCode() method. Uses method hash() of the {@code Objects} class get
     * the hash value of the class members.
     *
     * @return {@code int} value of the hash value fo the all class members
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName, orderDateTime, orderCost, status, listOfProducts);
    }

    /**
     * The standard method which represents the Product in the string value
     *
     * @return {@code String} the Product
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order{");
        sb.append(", userName='").append(userName);
        sb.append(", orderDate=").append(orderDateTime);
        sb.append(", orderCost=").append(orderCost);
        sb.append(", status=").append(status);
        sb.append(", listOfProducts=").append(listOfProducts);
        sb.append('}');
        return sb.toString();
    }
}
