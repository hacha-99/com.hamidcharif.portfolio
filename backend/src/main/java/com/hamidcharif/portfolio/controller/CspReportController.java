package com.hamidcharif.portfolio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CspReportController {

    private static final Logger log = LoggerFactory.getLogger(CspReportController.class);

    // allow all mediatypes 
    @PostMapping(value = "/csp-report", consumes = MediaType.ALL_VALUE)
    public void handleReport(@RequestBody String reportJson) {
        log.warn("CSP Violation Reported: {}", reportJson);
    }
}