package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.dto.ProductUpsertDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto findById(UUID id);

    String createProduct(ProductUpsertDto dto);

    String updateProduct(UUID id, ProductUpsertDto dto);

    void deleteById(UUID id);

    List<ProductDto> search(String keySearch, Integer pageSize, Integer pageNo);
}
