package org.example.orderservice.models.dtos.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExternalProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
}
