package com.example.projectfinal.entity;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String name;
    private int categoryId;
    private Float price;
    private Float salePrice;
    private String author;
    private int publisherId;
    private int publishYear;
    private String picture;
    private int number;
    private String description;
    private int page;
    private float rating;
    private boolean status;

    public Book() {
    }

    public Book(int id, String name, int categoryId, Float price, Float salePrice, String author, int publisherId, int publishYear, String picture, int number, String description, int page, float rating, boolean status) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.salePrice = salePrice;
        this.author = author;
        this.publisherId = publisherId;
        this.publishYear = publishYear;
        this.picture = picture;
        this.number = number;
        this.description = description;
        this.page = page;
        this.rating = rating;
        this.status = status;
    }

    public Book(String name, int categoryId, Float price, Float salePrice, String author, int publisherId, int publishYear, String picture, int number, String description, int page, float rating, boolean status) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.salePrice = salePrice;
        this.author = author;
        this.publisherId = publisherId;
        this.publishYear = publishYear;
        this.picture = picture;
        this.number = number;
        this.description = description;
        this.page = page;
        this.rating = rating;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}