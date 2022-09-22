package com.example.projectfinal.entity;

import java.io.Serializable;

public class Publisher implements Serializable {
    private int id;
    private String name;

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }

    public Publisher(int id, String name) {
        this.id = id;
        this.name = name;
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

    public String toString(){
        return this.name;
    }
}
