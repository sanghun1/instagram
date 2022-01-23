package com.cos.instagram.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class User {

    private String email;
    private String name;
    private String username;
    private String password;

    @ServerTimestamp
    private Date date;

    public User(){

    }

    public User(String email, String name, String username, String password, Date date) {
        this.email = email;
        this.name = name;
        this.username = username;
        this.password = password;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                '}';
    }
}
