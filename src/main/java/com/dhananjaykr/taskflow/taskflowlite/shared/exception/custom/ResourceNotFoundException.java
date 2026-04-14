package com.dhananjaykr.taskflow.taskflowlite.shared.exception.custom;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
