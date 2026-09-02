package org.example.productservice.mappers;

import org.example.productservice.models.dtos.res.ProductDTO;
import org.example.productservice.models.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product entity);
}
