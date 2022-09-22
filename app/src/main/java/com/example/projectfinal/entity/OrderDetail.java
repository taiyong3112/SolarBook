package com.example.projectfinal.entity;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private int id;
    private int orderId;
    private int bookId;
    private int bookCount;

    public OrderDetail() {
    }

    public OrderDetail(int id, int orderId, int bookId, int bookCount) {
        this.id = id;
        this.orderId = orderId;
        this.bookId = bookId;
        this.bookCount = bookCount;
    }

    public OrderDetail(int orderId, int bookId, int bookCount) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.bookCount = bookCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }
}
