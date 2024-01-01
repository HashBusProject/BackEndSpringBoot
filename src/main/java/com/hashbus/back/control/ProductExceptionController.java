package com.hashbus.back.control;

import com.hashbus.back.exceptions.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionController {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleUserE(UserException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
