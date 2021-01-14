package com.example.odprojekt.controller;

import com.example.odprojekt.entity.ResetPasswordToken;
import com.example.odprojekt.entity.User;
import com.example.odprojekt.payload.request.LoginRequest;
import com.example.odprojekt.payload.request.ResetPasswordRequest;
import com.example.odprojekt.payload.request.ResetPasswordTokenRequest;
import com.example.odprojekt.payload.request.SignupRequest;
import com.example.odprojekt.payload.response.LoginResponse;
import com.example.odprojekt.payload.response.MessageResponse;
import com.example.odprojekt.repository.ResetPasswordTokenRepository;
import com.example.odprojekt.repository.UserRepository;
import com.example.odprojekt.security.JwtUtils;
import com.example.odprojekt.security.RoleEnum;
import com.example.odprojekt.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Value("${domain}")
    private String domain;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils,
                          ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) throws InterruptedException {
        Thread.sleep(200);
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new MessageResponse("Bad credentials"));
        }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            String jwtClaims = jwt.substring(0, jwt.lastIndexOf("."));
            String jwtSignature = jwt.substring(jwt.lastIndexOf("."));

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            User user = userRepository.findByUsername(userDetails.getUsername()).get();
            user.setIp(user.getIp() + loginRequest.getIp() + ";");
            userRepository.save(user);

            Cookie authCookie = new Cookie("authSignature", jwtSignature);
            authCookie.setMaxAge(60 * 60);
            authCookie.setPath("/api");
            authCookie.setSecure(true);
            authCookie.setHttpOnly(true);
            response.addCookie(authCookie);


        return ResponseEntity.ok(new LoginResponse(jwtClaims,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                new Date(new Date().getTime() + 60 * 60 * 1000),
                roles, Arrays.asList(user.getIp().split(";"))));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is taken."));
        }

        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email is taken."));
        }

        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.USER);
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                roles
        );
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User was registered successfully"));
    }

    @GetMapping("/check/{login}")
    public ResponseEntity<?> checkLoginAvailability(@PathVariable("login") String login) {
        String response = userRepository.findByUsername(login).isPresent() ? "taken" : "free";
        return ResponseEntity.ok(new MessageResponse(response));
    }

    @PostMapping("/getResetPasswordToken")
    public ResponseEntity<?> getResetPasswordToken(@RequestBody ResetPasswordTokenRequest requestBody) {
        Optional<User> user = userRepository.findByEmail(requestBody.getEmail());
        if (user.isPresent()) {
            ResetPasswordToken token = resetPasswordTokenRepository.save(new ResetPasswordToken(user.get().getId()));
            System.out.println(token.generateLink());
            return ResponseEntity.ok(new MessageResponse(token.generateLink()));
        }
        return ResponseEntity.ok(new MessageResponse("Invalid email, not sent"));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String token_id = resetPasswordRequest.getToken();
        String password = resetPasswordRequest.getPassword();
        Optional<ResetPasswordToken> token = resetPasswordTokenRepository.findById(token_id);
        Calendar cal = Calendar.getInstance();
        if (token.isPresent() && token.get().getExpDate().after(cal.getTime())) {
            User user = userRepository.findById(token.get().getUser_id()).get();
            user.setPassword(encoder.encode(password));
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Password was reset successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Cannot reset password"));
    }
}
