package com.Revature.Models.DTOs;

public class OutgoingUserAndTokenDTO {
    OutgoingUser user;
    String token;

    public OutgoingUserAndTokenDTO() {
    }

    public OutgoingUserAndTokenDTO(OutgoingUser user, String token) {
        this.user = user;
        this.token = token;
    }

    public OutgoingUser getUser() {
        return user;
    }

    public void setUser(OutgoingUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "OutgoingUserAndTokenDTO{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
