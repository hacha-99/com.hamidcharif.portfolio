package com.hamidcharif.portfolio.exception;

public record ErrorResponse(
    String code,
    String message
) {}
