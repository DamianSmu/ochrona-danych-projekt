package com.example.odprojekt.payload.request;


import javax.validation.constraints.Size;

public class LoginRequest {

    @Size(min = 1, max = 1000)
    private String username;
    @Size(min = 1, max = 1000)
    private String password;
    private String ip;

    public LoginRequest(String username, String password, String ip) {
        this.username = username;
        this.password = password;
        this.ip = ip;
    }

    public LoginRequest() {
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
