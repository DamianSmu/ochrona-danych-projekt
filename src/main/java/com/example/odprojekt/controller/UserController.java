package com.example.odprojekt.controller;


import com.example.odprojekt.entity.User;
import com.example.odprojekt.payload.response.MessageResponse;
import com.example.odprojekt.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {

        User response = userRepository.findByUsername(authentication.getName()).get();
        return ResponseEntity.ok(response);
    }
}
