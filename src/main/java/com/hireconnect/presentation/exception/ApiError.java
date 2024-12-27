package com.hireconnect.presentation.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
    private int status;
    private String message;
    private List<String> errors;
    private String timestamp;

    public ApiError(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.status = status.value();
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now().toString();
    }
}
