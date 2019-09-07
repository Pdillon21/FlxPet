package com.example.fluxpet;

import android.content.Intent;

import java.util.List;

public class Pet {
    private Integer id;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private Category category;
    private String status;


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }



    public class Tag {
        private Integer id;
        private String name;
    }

    public class Category{
        private Integer id;
        private String name;
        }
}
