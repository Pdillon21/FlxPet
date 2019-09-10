package com.example.fluxpet.pojo;

import java.io.Serializable;

public class Tag implements Serializable {
    private long id;
    private String name;

    public Tag(){}

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
