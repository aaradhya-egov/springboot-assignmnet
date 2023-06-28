package com.springass1.assignment1.model;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class EgovUser {
    @Id
    private int id;
    private String name;
    private String gender;
    private String mobilenumber;
    private String address;

    public EgovUser() {
    }

    public EgovUser(int id, String name, String gender, String mobilenumber, String address) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.mobilenumber = mobilenumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "EgovUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", mobilenumber=" + mobilenumber +
                ", address='" + address + '\'' +
                '}';
    }
}
