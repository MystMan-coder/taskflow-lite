package com.dhananjaykr.taskflow.taskflowlite.shared.exception.custom;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
