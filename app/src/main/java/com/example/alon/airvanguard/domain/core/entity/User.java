package com.example.alon.airvanguard.domain.core.entity;

/**
 * Created by alon on 6/17/17.
 */

public class User {

    private String name;
    private String photoUrl;

    public User() {
    }

    public User(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
