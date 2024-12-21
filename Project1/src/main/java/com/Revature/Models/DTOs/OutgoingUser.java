package com.Revature.Models.DTOs;

import com.Revature.Models.User;

public class OutgoingUser {
    private int userId;
    private String username;
    private String role;
    private String firstname;
    private String lastname;

    public OutgoingUser() {
    }

    public OutgoingUser(int userId, String username, String role, String firstname, String lastname) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
    }



    public OutgoingUser(User user){
        userId = user.getUserId();
        username = user.getUsername();
        role = user.getRole();
        firstname = user.getFirstName();
        lastname = user.getLastName();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

