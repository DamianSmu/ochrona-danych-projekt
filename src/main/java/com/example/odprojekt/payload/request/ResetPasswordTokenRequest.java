package com.example.odprojekt.payload.request;

public class ResetPasswordTokenRequest {
    private String email;

    public ResetPasswordTokenRequest(String email) {
        this.email = email;
    }

    public ResetPasswordTokenRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
