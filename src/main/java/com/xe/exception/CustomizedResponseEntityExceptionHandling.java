package com.xe.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionHandling> handleAllException(Exception e, WebRequest request) {
        final ExceptionHandling exceptionHandling = new ExceptionHandling(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionHandling, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ExceptionHandling> handleUserNotFoundException(Exception e, WebRequest request) {
        final ExceptionHandling exceptionHandling = new ExceptionHandling(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionHandling, HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ExceptionHandling exceptionHandling = new ExceptionHandling(new Date(), ex.getMessage(), ex.getBindingResult().toString());
        return new ResponseEntity<>(exceptionHandling, HttpStatus.BAD_GATEWAY);
    }
}
