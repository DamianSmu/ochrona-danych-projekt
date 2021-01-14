package com.example.odprojekt.payload.response;


import java.util.Date;
import java.util.List;

public class LoginResponse {

    private String accessToken;
    private String id;
    private String username;
    private String email;
    private Date expDate;
    private final List<String> roles;
    private List<String> ip;

    public LoginResponse(String accessToken, String id, String username, String email, Date expDate, List<String> roles, List<String> ip) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.expDate = expDate;
        this.roles = roles;
        this.ip = ip;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public List<String> getIp() {
        return ip;
    }

    public void setIp(List<String> ip) {
        this.ip = ip;
    }
}