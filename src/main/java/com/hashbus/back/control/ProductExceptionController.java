package com.hashbus.back.control;

import com.hashbus.back.exceptions.UserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionController {
    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<?> handleUserE(UserException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("message", e.getMessage());
        return ResponseEntity.badRequest().headers(headers).build();
    }
}
