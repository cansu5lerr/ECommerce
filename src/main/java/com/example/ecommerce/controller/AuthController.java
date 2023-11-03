package com.example.ecommerce.controller;
import com.example.ecommerce.model.dto.request.LoginRequest;
import com.example.ecommerce.model.dto.request.SignupRequest;
import com.example.ecommerce.model.dto.response.UserDetailsResponse;
import com.example.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.ecommerce.service.security.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService userAuthService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userAuthService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return userAuthService.registerUser(signupRequest);
    }

    @GetMapping("/user/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String token) {

        UserDetailsResponse userProfile = userDetailsService.getUserProfile(token);
        return ResponseEntity.ok(userProfile);
    }

    @PostMapping("/user/profile")
    public ResponseEntity<?> updateUserProfile(@RequestHeader("Authorization") String token, @RequestBody UserDetailsResponse userDetatilsResponse) {
        userDetatilsResponse = userDetailsService.updateUserProfile(token, userDetatilsResponse);
        return ResponseEntity.ok(userDetatilsResponse);
    }


}