package com.example.fluxpet.pojo;

import java.io.Serializable;

public class Category implements Serializable {
    private long id;
    private String name;

    public Category (){}

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }
}
