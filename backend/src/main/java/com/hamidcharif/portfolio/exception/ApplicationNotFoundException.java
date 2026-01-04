package com.hamidcharif.portfolio.exception;

public class ApplicationNotFoundException extends RuntimeException{
    public ApplicationNotFoundException() {
        super("Application does not exist.");
    }

    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
