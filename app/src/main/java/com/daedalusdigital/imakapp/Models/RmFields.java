package com.daedalusdigital.imakapp.Models;

import io.realm.RealmObject;

/**
 * Created by Cyrill Zungu on 12-Dec-17.
 */

public class RmFields extends RealmObject {

    int clnt_id ;
    String fname , Sname, email, client_password , client_username , profile_picture , state , city , Street , house_unit_number , phone_number;

    public int getClnt_id() {
        return clnt_id;
    }

    public void setClnt_id(int clnt_id) {
        this.clnt_id = clnt_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClient_password() {
        return client_password;
    }

    public void setClient_password(String client_password) {
        this.client_password = client_password;
    }

    public String getClient_username() {
        return client_username;
    }

    public void setClient_username(String client_username) {
        this.client_username = client_username;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getHouse_unit_number() {
        return house_unit_number;
    }

    public void setHouse_unit_number(String house_unit_number) {
        this.house_unit_number = house_unit_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
