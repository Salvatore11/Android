package com.informatica.unipr.esercizio2;

import java.io.Serializable;

/**
 * Created by utente on 05/05/2018.
 */

public class AccountData implements Serializable{

    private String username;
    private String nome;
    private String phone;

    public AccountData(String nome, String phone){
        this.nome= nome;
        this.phone= phone;
    }

    public AccountData(){}

    public String getUsername(){
        return username;

    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome= nome;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone= phone;
    }
}
