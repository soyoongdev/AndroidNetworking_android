package com.example.androidnetworking.model;

public class Category {
    private String id;
    private String name;
    private String created_at;
    private String updated_at;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String id, String name, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    @Override
    public String toString() {
        return getName();
    }
}
