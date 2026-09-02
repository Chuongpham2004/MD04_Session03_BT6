package org.example.orderservice.models.dtos.req;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private Long customerId;
    private Long productId;
}
