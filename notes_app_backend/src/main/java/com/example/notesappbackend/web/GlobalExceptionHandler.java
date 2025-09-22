package com.example.notesappbackend.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * Ocean Professional error responses: clear and minimal.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> onValidation(MethodArgumentNotValidException ex) {
        var fieldError = ex.getBindingResult().getFieldError();
        String msg = fieldError != null ? fieldError.getField() + " " + fieldError.getDefaultMessage() : "Validation error";
        return ResponseEntity.badRequest().body(msg);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> onIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
