package com.hamidcharif.portfolio.DTO;

import jakarta.validation.constraints.NotBlank;

public record ApplicationDTO(
        @NotBlank String coverLetter,

        @NotBlank String username) {
}