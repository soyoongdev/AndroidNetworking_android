package com.example.androidnetworking.model;

public class ServerResponse {
    private boolean state;
    private String message;
    private User user;

    public ServerResponse(boolean state, String message) {
        this.state = state;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
