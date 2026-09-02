package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.models.dtos.req.OrderRequestDTO;
import org.example.orderservice.models.dtos.res.OrderResponseDTO;
import org.example.orderservice.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }
}
