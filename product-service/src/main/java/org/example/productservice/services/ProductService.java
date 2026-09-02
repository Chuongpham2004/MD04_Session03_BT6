package org.example.productservice.services;

import lombok.RequiredArgsConstructor;
import org.example.productservice.exceptions.ResourceNotFoundException;
import org.example.productservice.mappers.ProductMapper;
import org.example.productservice.models.dtos.res.ProductDTO;
import org.example.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
}
