package com.springass1.assignment1.model;

import java.util.UUID;

public class Searchuser {
    private String id;
    private String mobilenumber;
    private boolean active;


    @Override
    public String toString() {
        return "Searchuser{" +
                "id=" + id +
                ", mobilenumber='" + mobilenumber + '\'' +
                '}';
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getId() {
        return id;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }
}
