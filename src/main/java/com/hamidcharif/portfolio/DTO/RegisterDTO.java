package com.hamidcharif.portfolio.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotBlank String name,

        @NotBlank String username,

        @Size(min = 7) String password) {
}