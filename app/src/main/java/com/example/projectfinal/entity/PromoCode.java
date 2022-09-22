package com.example.projectfinal.entity;

import java.io.Serializable;

public class PromoCode implements Serializable {
    private int id;
    private String name;
    private String description;
    private boolean type;
    private int value;

    public PromoCode() {
    }

    public PromoCode(int id, String name, String description, boolean type, int value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    public PromoCode(String name, String description, boolean type, int value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
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

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
