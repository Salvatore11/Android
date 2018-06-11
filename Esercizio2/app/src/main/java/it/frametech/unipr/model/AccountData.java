package it.frametech.unipr.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Bertoletti on 20/07/2016.
 */

public class AccountData implements Serializable {

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
