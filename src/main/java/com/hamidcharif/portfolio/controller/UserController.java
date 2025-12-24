package com.hamidcharif.portfolio.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamidcharif.portfolio.security.AuthRequest;
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
    public String addNewUser(@RequestBody @Valid RegisterRequest userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) {
        System.out.println("Login-Versuch für: " + authRequest.getUsername() + ", Password: " + authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.createToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}