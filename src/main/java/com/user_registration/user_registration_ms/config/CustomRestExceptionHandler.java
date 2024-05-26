package com.user_registration.user_registration_ms.config;

import com.user_registration.user_registration_ms.config.exceptions.CustomRestException;
import com.user_registration.user_registration_ms.config.exceptions.GenericException;
import com.user_registration.user_registration_ms.config.exceptions.NotFoundException;
import com.user_registration.user_registration_ms.config.exceptions.UnauthorizedException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss";

    private static final String GENERIC_ERROR_MESSAGE = "Generic Error Message";

    @ExceptionHandler({GenericException.class})
    public ResponseEntity<Object> handleGenericException(GenericException ex) {
        List<String> errors = ex.getErrors() != null ? ex.getErrors() : Arrays.asList(GENERIC_ERROR_MESSAGE);

        CustomRestException error = new CustomRestException(
                HttpStatus.BAD_REQUEST, ex.getMessage(),
                errors,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                HttpStatus.BAD_REQUEST.value(),
                ex.getType()
        );

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {

        CustomRestException error = new CustomRestException(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                GENERIC_ERROR_MESSAGE,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                HttpStatus.NOT_FOUND.value()
        );

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {

        CustomRestException error = new CustomRestException(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                GENERIC_ERROR_MESSAGE,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                HttpStatus.UNAUTHORIZED.value()
        );

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {

        CustomRestException error = new CustomRestException(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(error, error.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = ex.getFieldError() != null ?  ex.getFieldError().getDefaultMessage() : ex.getMessage();

        CustomRestException error = new CustomRestException(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                ex.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(error, error.getStatus());
    }

}
