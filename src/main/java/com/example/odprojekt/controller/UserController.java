package com.example.odprojekt.controller;


import com.example.odprojekt.entity.BlockedToken;
import com.example.odprojekt.entity.User;
import com.example.odprojekt.payload.request.ChangePasswordRequest;
import com.example.odprojekt.payload.response.MessageResponse;
import com.example.odprojekt.repository.BlockedTokensRepository;
import com.example.odprojekt.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final BlockedTokensRepository blockedTokensRepository;

    public UserController(UserRepository userRepository, PasswordEncoder encoder, BlockedTokensRepository blockedTokensRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.blockedTokensRepository = blockedTokensRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {

        User response = userRepository.findByUsername(authentication.getName()).get();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Authentication authentication) {
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();
        User user = userRepository.findByUsername(authentication.getName()).get();
        if (!encoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Old password incorrect"));
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Password was reset successfully"));
    }

    @GetMapping("/logoutAll")
    public ResponseEntity<?> logoutAll(Authentication authentication) {
        blockedTokensRepository.save(new BlockedToken(authentication.getName()));
        return ResponseEntity.ok().build();
    }
}
