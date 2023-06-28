package com.springass1.assignment1.model;

public class Searchuser {
    private int id;
    private String mobilenumber;

    @Override
    public String toString() {
        return "Searchuser{" +
                "id=" + id +
                ", mobilenumber='" + mobilenumber + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public int getId() {
        return id;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }
}
