package org.example.orderservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.exceptions.ServiceUnavailableException;
import org.example.orderservice.models.dtos.req.OrderRequestDTO;
import org.example.orderservice.models.dtos.res.ExternalProductDTO;
import org.example.orderservice.models.dtos.res.OrderResponseDTO;
import org.example.orderservice.models.entities.Order;
import org.example.orderservice.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderResponseDTO createOrder(OrderRequestDTO req) {
        String productUrl = "http://localhost:8082/api/v1/products/" + req.getProductId();
        ExternalProductDTO productInfo;

        try {
            productInfo = restTemplate.getForObject(productUrl, ExternalProductDTO.class);
            if (productInfo == null) throw new RestClientException("No data");
        } catch (ResourceAccessException e) {
            log.error("Kết nối Product Service timeout/thất bại: {}", e.getMessage());
            throw new ServiceUnavailableException("Dịch vụ sản phẩm hiện không khả dụng, vui lòng thử lại sau.");
        } catch (RestClientException e) {
            log.error("Lỗi khi lấy thông tin sản phẩm: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể xác thực thông tin sản phẩm lúc này.");
        }

        Order order = Order.builder()
                .customerId(req.getCustomerId())
                .productId(productInfo.getId())
                .totalAmount(productInfo.getPrice())
                .status("CREATED")
                .build();

        Order savedOrder = orderRepository.save(order);

        return OrderResponseDTO.builder()
                .id(savedOrder.getId())
                .customerId(savedOrder.getCustomerId())
                .productId(savedOrder.getProductId())
                .totalAmount(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus())
                .orderDate(savedOrder.getOrderDate())
                .build();
    }
}
