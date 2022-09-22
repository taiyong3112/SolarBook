package com.example.projectfinal.entity;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private int id;
    private int userId;
    private Date orderDate;
    private int promoCodeId;
    private float totalPrice;
    private int cityId;
    private String shippingAddress;
    private int shippingPrice;
    private int paymentId;
    private String note;
    private Date createdDate;

    public Order() {
    }

    public Order(int id, int userId, Date orderDate, int promoCodeId, float totalPrice, int cityId, String shippingAddress, int shippingPrice, int paymentId, String note, Date createdDate) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.promoCodeId = promoCodeId;
        this.totalPrice = totalPrice;
        this.cityId = cityId;
        this.shippingAddress = shippingAddress;
        this.shippingPrice = shippingPrice;
        this.paymentId = paymentId;
        this.note = note;
        this.createdDate = createdDate;
    }

    public Order(int userId, Date orderDate, int promoCodeId, float totalPrice, int cityId, String shippingAddress, int shippingPrice, int paymentId, String note, Date createdDate) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.promoCodeId = promoCodeId;
        this.totalPrice = totalPrice;
        this.cityId = cityId;
        this.shippingAddress = shippingAddress;
        this.shippingPrice = shippingPrice;
        this.paymentId = paymentId;
        this.note = note;
        this.createdDate = createdDate;
    }

    public Order(int userId, Date orderDate, float totalPrice, int cityId, String shippingAddress, int shippingPrice, int paymentId, String note, Date createdDate) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.cityId = cityId;
        this.shippingAddress = shippingAddress;
        this.shippingPrice = shippingPrice;
        this.paymentId = paymentId;
        this.note = note;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getPromoCodeId() {
        return promoCodeId;
    }

    public void setPromoCodeId(int promoCodeId) {
        this.promoCodeId = promoCodeId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(int shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
