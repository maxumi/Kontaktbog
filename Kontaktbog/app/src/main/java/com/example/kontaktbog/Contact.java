package com.example.kontaktbog;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String email;
    private String phone; // Constructor, getters og setters }

    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }

    public void setName(String _name){
        name = _name;
    }

    public void setEmail(String _email){
        email = _email;
    }

    public void setPhone(String _phone){
        phone = _phone;
    }
}

