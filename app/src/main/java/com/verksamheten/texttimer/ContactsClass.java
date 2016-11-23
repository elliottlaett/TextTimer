package com.verksamheten.texttimer;

import java.io.Serializable;

/**
 * Created by Elliott on 2016-10-02.
 */
public class ContactsClass implements Serializable {

    private String id;
    private String name;
    private String phoneNumber;
    private String email;


    public ContactsClass(String id, String name, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public ContactsClass() {

    }

    public ContactsClass(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    ContactsClass getContact(int id){


        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
