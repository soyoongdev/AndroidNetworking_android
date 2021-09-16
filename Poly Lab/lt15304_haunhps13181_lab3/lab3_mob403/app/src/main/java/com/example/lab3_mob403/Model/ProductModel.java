package com.example.lab3_mob403.Model;

public class ProductModel {
    private String id;
    private String type;
    private String title;
    private String price;
    private String image;

    public ProductModel() {
    }

    public ProductModel(String id, String type, String title, String price, String image) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
