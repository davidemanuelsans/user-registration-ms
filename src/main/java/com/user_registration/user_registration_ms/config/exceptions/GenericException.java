package com.user_registration.user_registration_ms.config.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class GenericException extends Exception {
    List<String> errors;
    String type;

    public GenericException() {
        super();
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, String type) {
        super(message);
        this.type = type;
    }

    public GenericException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

    protected GenericException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}