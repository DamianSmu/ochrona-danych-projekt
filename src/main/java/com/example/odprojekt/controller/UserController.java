package com.example.odprojekt.controller;


import com.example.odprojekt.entity.ResetPasswordToken;
import com.example.odprojekt.entity.User;
import com.example.odprojekt.payload.request.ChangePasswordRequest;
import com.example.odprojekt.payload.request.ResetPasswordRequest;
import com.example.odprojekt.payload.response.MessageResponse;
import com.example.odprojekt.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {

        User response = userRepository.findByUsername(authentication.getName()).get();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> resetPassword(@RequestBody ChangePasswordRequest changePasswordRequest, Authentication authentication) {
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();
        User user = userRepository.findByUsername(authentication.getName()).get();
        if(!user.getPassword().equals(encoder.encode(oldPassword))){
            return ResponseEntity.badRequest().body(new MessageResponse("Old password incorrect"));
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Password was reset successfully"));
    }
}
