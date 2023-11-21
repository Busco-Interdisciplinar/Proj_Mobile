package com.example.busco.Api.Models;

public class Base64Image {
    private Integer id;
    private String image;

    public Base64Image(Integer id, String image) {
        this.id = id;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Base64Image{" +
                "id=" + id +
                ", image='" + image + '\'' +
                '}';
    }
}

