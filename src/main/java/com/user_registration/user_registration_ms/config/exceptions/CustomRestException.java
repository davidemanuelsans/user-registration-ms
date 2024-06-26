package com.user_registration.user_registration_ms.config.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
public class CustomRestException {

    private HttpStatus status;
    private int code;
    private String timestamp;
    private String message;
    String type;
    private List<String> errors;

    public CustomRestException(HttpStatus status, String message, List<String> errors, String timestamp, int code) {
        super();
        this.code = code;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public CustomRestException(HttpStatus status, String message, String error, String timestamp, int code) {
        super();
        this.code = code;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public CustomRestException(HttpStatus status, String message, List<String> errors, String timestamp, int code, String type) {
        super();
        this.code = code;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.type = type;
    }

    public CustomRestException(HttpStatus status, String message, String error, String timestamp, int code, String type) {
        super();
        this.code = code;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
        this.type = type;
    }
}