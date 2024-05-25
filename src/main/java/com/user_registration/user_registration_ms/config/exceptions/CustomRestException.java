package com.user_registration.user_registration_ms.config.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

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

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{\"FlashCustomError\":{"
            + "\"status\":\"" + status + "\""
            + ", \"code\":\"" + code + "\""
            + ", \"timestamp\":\"" + timestamp + "\""
            + ", \"message\":\"" + message + "\""
            + ", \"errors\":" + errors
            + ", \"type\":" + type
            + "}}";
    }
}