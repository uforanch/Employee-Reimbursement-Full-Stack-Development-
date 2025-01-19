package com.Revature.Models.DTOs;

import com.Revature.Models.User;

import java.util.UUID;

public class OutgoingUser {
    private UUID userId;
    private String username;
    private String role;
    private String firstname;
    private String lastname;
    private String shortId;

    public OutgoingUser() {
    }

    public OutgoingUser(UUID userId, String username, String role, String firstname, String lastname, String shortId) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.shortId = shortId;
    }



    public OutgoingUser(User user){
        userId = user.getUserId();
        username = user.getUsername();
        role = user.getRole();
        firstname = user.getFirstName();
        lastname = user.getLastName();
        shortId = user.getShortId();
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    @Override
    public String toString() {
        return "OutgoingUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}

