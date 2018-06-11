package com.informatica.unipr.provaesame;

import java.io.Serializable;

public class AccountData implements Serializable{

    private String name;
    private String phone;
    private String username;


    public AccountData(String name, String phone){
        this.name= name;
        this.phone = phone;
    }


    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setName(String name){
        this.name= name;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return phone;
    }
}
