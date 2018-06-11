package com.informatica.unipr.myappbella;

import java.io.Serializable;

public class AccountData implements Serializable{


    private String username;
    private String name;
    private String phone;

    public AccountData (String name, String phone) {
        this.name = name;
        this.phone = phone;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccountData() {}
}
