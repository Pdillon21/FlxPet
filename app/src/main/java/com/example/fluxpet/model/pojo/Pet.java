package com.example.fluxpet.model.pojo;





import java.io.Serializable;
import java.util.List;


public class Pet implements Serializable {
    private long id;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private Category category;
    private String status;





    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public Category getCategory() {
        return category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
