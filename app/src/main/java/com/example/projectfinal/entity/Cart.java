package com.example.projectfinal.entity;

import java.io.Serializable;

public class Cart implements Serializable {
    private int id;
    private int bookCount;
    private int bookId;
    private int userId;

    public Cart() {
    }

    public Cart(int id, int bookCount, int bookId, int userId) {
        this.id = id;
        this.bookCount = bookCount;
        this.bookId = bookId;
        this.userId = userId;
    }

    public Cart(int bookCount, int bookId, int userId) {
        this.bookCount = bookCount;
        this.bookId = bookId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
