package com.example.androidnetworking.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String datetime;
    private String updated_at;

    public User(int id, String username, String email, String datetime, String updated_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.datetime = datetime;
        this.updated_at = updated_at;
    }

    public User(int id, String username, String email, String datetime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.datetime = datetime;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
