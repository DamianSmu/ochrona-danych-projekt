package com.example.odprojekt.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class LoginResponse {
    @Getter
    @Setter
    private String token;
    private final String type = "Bearer";
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private final List<String> roles;

    public LoginResponse(String accessToken, String id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

}