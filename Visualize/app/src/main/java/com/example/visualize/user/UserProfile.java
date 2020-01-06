package com.example.visualize.user;


public class UserProfile {

    private String username;

    public UserProfile(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return String.format("%s", getUsername());
    }

}
