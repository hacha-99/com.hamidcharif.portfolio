package com.hamidcharif.portfolio.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamidcharif.portfolio.security.AuthRequest;
import com.hamidcharif.portfolio.security.AuthResponse;
import com.hamidcharif.portfolio.security.RegisterRequest;
import com.hamidcharif.portfolio.service.JwtService;
import com.hamidcharif.portfolio.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {

    private UserService userService;

    private JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService userRegistrationService,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userService = userRegistrationService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> addNewUser(@RequestBody @Valid RegisterRequest userDTO) {
        userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        String token = jwtService.createToken(authentication);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}