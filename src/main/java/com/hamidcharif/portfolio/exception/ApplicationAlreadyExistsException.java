package com.hamidcharif.portfolio.exception;

public class ApplicationAlreadyExistsException extends RuntimeException{
    public ApplicationAlreadyExistsException() {
        super("Application already exists.");
    }

    public ApplicationAlreadyExistsException(String message) {
        super(message);
    }
}
