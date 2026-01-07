package com.hamidcharif.portfolio.DTO;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @Pattern(regexp = "^[a-zA-Z0-9]+$")
        String username,

        @Pattern(regexp = "^[a-zA-Z0-9]$")
        @Size(min = 7) 
        String password) {
}