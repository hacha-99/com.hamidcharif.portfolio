package com.hamidcharif.portfolio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamidcharif.portfolio.DTO.ApplicationDTO;
import com.hamidcharif.portfolio.service.ApplicationService;

/**
 * ApplicationController.java provides endpoint to load applications from mongodb.
 */

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<ApplicationDTO> loadApplication(Authentication authentication){
        String username = authentication.getName();
        ApplicationDTO applicationDTO = applicationService.loadApplication(username);
        return ResponseEntity.ok(applicationDTO);
    }
}
