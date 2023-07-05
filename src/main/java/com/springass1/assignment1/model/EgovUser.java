package com.springass1.assignment1.model;

import java.util.UUID;

public class EgovUser {
    private String id;
    private String name;
    private String gender;
    private String mobilenumber;
    private Address address;
    private Boolean active;
    private Long createdtime;

    public EgovUser() {
    }



    public EgovUser(String id, String name, String gender, String mobilenumber, Address address,Boolean active, Long createdtime) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.mobilenumber = mobilenumber;
        this.address = address;
        this.active=active;
        this.createdtime=createdtime;
    }


    public Long  getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Long  createdtime) {
        this.createdtime = createdtime;
    }

    public String getId() {
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

    public Address getAddress() {
        return address;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setId(String id) {
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

    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "EgovUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", mobilenumber='" + mobilenumber + '\'' +
                ", address='" + address + '\'' +
                ", active=" + active +
                ", createdtime=" + createdtime +
                '}';
    }

}
