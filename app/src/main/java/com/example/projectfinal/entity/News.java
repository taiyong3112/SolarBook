package com.example.projectfinal.entity;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable {
    private int id;
    private String name;
    private String description;
    private String detail;
    private String picture;
    private Date createdDate;

    public News() {
    }

    public News(int id, String name, String description, String detail, String picture, Date createdDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.detail = detail;
        this.picture = picture;
        this.createdDate = createdDate;
    }

    public News(String name, String description, String detail, String picture, Date createdDate) {
        this.name = name;
        this.description = description;
        this.detail = detail;
        this.picture = picture;
        this.createdDate = createdDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
