package com.example.odprojekt.payload.request;

public class ResetPasswordRequest {
    private String password;
    private String token;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String password, String token) {
        this.password = password;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
