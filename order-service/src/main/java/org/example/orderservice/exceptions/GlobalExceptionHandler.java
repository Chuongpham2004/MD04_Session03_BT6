package org.example.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ApiResponseError> handleServiceUnavailable(ServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                ApiResponseError.builder().timestamp(LocalDateTime.now())
                        .status(503).error("Service Unavailable").message(ex.getMessage()).build()
        );
    }
}
