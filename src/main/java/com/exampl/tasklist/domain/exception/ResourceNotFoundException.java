package com.exampl.tasklist.domain.exception;

public class ResourceNotFoundException extends RuntimeException{ //uncheck
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
