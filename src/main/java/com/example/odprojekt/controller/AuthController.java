package com.example.odprojekt.controller;

import com.example.odprojekt.entity.User;
import com.example.odprojekt.payload.request.LoginRequest;
import com.example.odprojekt.payload.request.SignupRequest;
import com.example.odprojekt.payload.response.LoginResponse;
import com.example.odprojekt.payload.response.MessageResponse;
import com.example.odprojekt.repository.UserRepository;
import com.example.odprojekt.security.JwtUtils;
import com.example.odprojekt.security.RoleEnum;
import com.example.odprojekt.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String jwtClaims = jwt.substring(0, jwt.lastIndexOf("."));
        String jwtSignature = jwt.substring(jwt.lastIndexOf("."));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Cookie authCookie = new Cookie("authSignature", jwtSignature);
        authCookie.setMaxAge(60*60);
        //authCookie.setDomain("localhost");
        authCookie.setPath("/");
        //authCookie.setSecure(true);
        //authCookie.setHttpOnly(true);
        response.addCookie(authCookie);


        return ResponseEntity.ok(new LoginResponse(jwtClaims,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Użytkownik o podanej nazwie istnieje już w bazie."));
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

        return ResponseEntity.ok(new MessageResponse("Użytkownik zarejestrowany pomyślnie!"));
    }

    @GetMapping("/check/{login}")
    public ResponseEntity<?> checkLoginAvailability(@PathVariable("login") String login) {
        String response = userRepository.findByUsername(login).isPresent() ? "taken" : "free";
        return ResponseEntity.ok(new MessageResponse(response));
    }
}
