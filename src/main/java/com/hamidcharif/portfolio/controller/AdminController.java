package com.hamidcharif.portfolio.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamidcharif.portfolio.DTO.RegisterRequest;
import com.hamidcharif.portfolio.service.ApplicationService;
import com.hamidcharif.portfolio.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ApplicationService applicationService;

    public AdminController(UserService userService, ApplicationService applicationService) {
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<Void> addUser(@RequestBody @Valid RegisterRequest userDTO) {
        userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/addApplication")
    public ResponseEntity<Void> addApplication(@RequestBody @Valid TYPE applicationDTO) {
        // TODO: ADD DTO CLASS FOR APPLICATION AND USE HERE (HOW TO NAME IT?)
        applicationService.addApplication(applicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
