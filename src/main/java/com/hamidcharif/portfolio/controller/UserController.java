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
import com.hamidcharif.portfolio.service.CustomUserDetailsService;
import com.hamidcharif.portfolio.service.JwtService;

@RestController
@RequestMapping("/auth")
public class UserController {

    private CustomUserDetailsService customUserDetailsService;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    public UserController() {

    }

    public UserController(CustomUserDetailsService customUserDetailsService,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // @PostMapping("/addNewUser")
    // public String addNewUser(@RequestBody UserDTO userDTO) {
    //     // perhaps convert userDTO to normal User
    //     return customUserDetailsService.addUser(userDTO);
    // }

    @PostMapping("/createToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.createToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}