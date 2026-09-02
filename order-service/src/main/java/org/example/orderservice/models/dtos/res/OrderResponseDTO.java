package org.example.orderservice.models.dtos.res;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private Long productId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime orderDate;
}
