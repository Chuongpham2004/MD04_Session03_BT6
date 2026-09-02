package org.example.productservice.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponseError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
