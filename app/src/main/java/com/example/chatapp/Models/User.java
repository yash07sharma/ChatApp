package com.example.chatapp.Models;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String phone;
    private int status;//active or not

    public User(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getStatus() {
        return status;
    }
}
