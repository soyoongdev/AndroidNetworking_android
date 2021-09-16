package com.example.androidnetworking.model;

public class Product {
    private String id;
    private String name;
    private String price;
    private String created_at;
    private String updated_at;
    private String id_type;

    public Product() {
    }

    public Product(String name, String price, String id_type) {
        this.name = name;
        this.price = price;
        this.id_type = id_type;
    }

    // Update
    public Product(String id, String name, String price, String id_type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.id_type = id_type;
    }

    public Product(String id, String name, String price, String created_at, String updated_at, String id_type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id_type = id_type;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public Product(String id, String name, String price, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
