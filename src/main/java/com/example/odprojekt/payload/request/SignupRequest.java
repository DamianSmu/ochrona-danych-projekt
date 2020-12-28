package com.example.odprojekt.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class SignupRequest {

    @NotBlank
    @Getter
    @Setter
    private String username;

    @NotBlank
    @Getter
    @Setter
    private String email;

    @NotBlank
    @Getter
    @Setter
    private String password;

}
