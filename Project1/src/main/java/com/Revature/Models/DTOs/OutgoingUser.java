package com.Revature.Models.DTOs;

import com.Revature.Models.User;

public class OutgoingUser {
    private int userId;
    private String username;
    private String role;

    public OutgoingUser() {
    }

    public OutgoingUser(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public OutgoingUser(User user){
        userId = user.getUserId();
        username = user.getUsername();
        role = user.getRole();
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

    @Override
    public String toString() {
        return "OutgoingUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
