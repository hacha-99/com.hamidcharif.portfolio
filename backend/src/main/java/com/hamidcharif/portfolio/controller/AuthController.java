package com.hamidcharif.portfolio.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamidcharif.portfolio.DTO.LoginDTO;
import com.hamidcharif.portfolio.service.JwtService;
import com.hamidcharif.portfolio.service.UserService;

import jakarta.validation.Valid;

/**
 * AuthController.java provides endpoint to receive credentials and generates
 * and returns jwt (cookie) upon authentication.
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userRegistrationService,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));

            String token = jwtService.createToken(authentication);

            ResponseCookie cookie = ResponseCookie.from("access_token", token)
                    .httpOnly(true)
                    .secure(true) // TODO PROD: true | DEV: false
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(Duration.ofMinutes(15))
                    .build();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "invalid_credentials"));
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {

        ResponseCookie deleteCookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .build();
    }

    @GetMapping("/csrf")
    public void csrf() {

    }
}