package com.chamo.BackEnd.exception;

import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {
    private final String errorCode;

    public InvalidInputException(String message) {
        super(message);
        this.errorCode = "VALIDATION_ERROR";
    }

    public InvalidInputException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}