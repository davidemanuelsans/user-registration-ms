package com.user_registration.user_registration_ms.config.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ConflictResourceException extends Exception {
    private HttpStatus status;
    private int code;
    private String timestamp;
    private String message;
    String type;
    private List<String> errors;

    public ConflictResourceException(HttpStatus status, String message, List<String> errors, String timestamp, int code) {
        super();
        this.code = code;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ConflictResourceException(HttpStatus status, String message, String error, String timestamp, int code) {
        super();
        this.code = code;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public ConflictResourceException(HttpStatus status, String message, List<String> errors, String timestamp, int code, String type) {
        super();
        this.code = code;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.type = type;
    }

    public ConflictResourceException(HttpStatus status, String message, String error, String timestamp, int code, String type) {
        super();
        this.code = code;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
        this.type = type;
    }

    public ConflictResourceException(String message) {
        super(message);
    }
}
