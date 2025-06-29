package com.chamo.BackEnd.exception;

import com.chamo.BackEnd.dto.*;
import com.chamo.BackEnd.dto.auth.LoginResDto;
import com.chamo.BackEnd.dto.auth.RegisterResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handleInvalidInputException(InvalidInputException ex, WebRequest request) {
        // For auth endpoints, return the specific DTOs
        if (isAuthEndpoint(request)) {
            if (isLoginEndpoint(request)) {
                return ResponseEntity.badRequest().body(
                        new LoginResDto(null, null, ex.getErrorCode(), ex.getMessage())
                );
            } else if (isRegisterEndpoint(request)) {
                return ResponseEntity.badRequest().body(
                        new RegisterResDto(null, ex.getMessage())
                );
            }
        }

        // For all other endpoints, use the standard error DTO
        return ResponseEntity.badRequest().body(
                new ErrorResDto(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getErrorCode(),
                        ex.getMessage(),
                        getCleanPath(request))
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        // For auth endpoints
        if (isAuthEndpoint(request)) {
            if (isLoginEndpoint(request)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        new LoginResDto(null, null, "SYSTEM_ERROR", "Internal server error")
                );
            } else if (isRegisterEndpoint(request)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        new RegisterResDto(null, "Registration processing failed")
                );
            }
        }

        // For all other endpoints
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResDto(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "INTERNAL_ERROR",
                        "An unexpected error occurred",
                        getCleanPath(request))
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResDto> handleProductNotFound(ProductNotFoundException ex, WebRequest request) {
        ErrorResDto errorResponse = new ErrorResDto(
                HttpStatus.NOT_FOUND.value(),
                "PRODUCT_NOT_FOUND",
                ex.getMessage(),
                getCleanPath(request)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Helper methods
    private boolean isAuthEndpoint(WebRequest request) {
        return request.getDescription(false).contains("/api/auth/");
    }

    private boolean isLoginEndpoint(WebRequest request) {
        return request.getDescription(false).contains("/login");
    }

    private boolean isRegisterEndpoint(WebRequest request) {
        return request.getDescription(false).contains("/register");
    }

    private String getCleanPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}