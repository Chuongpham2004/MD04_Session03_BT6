package org.example.productservice.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponseError error = ApiResponseError.builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(404)
                .error("Not Found")
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(404).body(error);
    }
}
