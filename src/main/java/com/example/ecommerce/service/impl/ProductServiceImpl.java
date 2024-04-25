package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.dto.ProductUpsertDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.EComException;
import com.example.ecommerce.exception.ErrorCode;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductDto findById(UUID id) {
        return productRepository.findById(id)
                .map(product -> ProductDto
                        .builder()
                        .id(product.getId())
                        .description(product.getDescription())
                        .name(product.getName())
                        .quantityInStock(product.getQuantityInStock())
                        .unitPrice(product.getUnitPrice())
                        .build())
                .orElseThrow(() -> new EComException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public String createProduct(ProductUpsertDto dto) {
        var product = new Product();
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setUnitPrice(dto.getUnitPrice());
        product.setQuantityInStock(dto.getQuantityInStock());
        productRepository.save(product);
        return product.getId().toString();
    }

    @Override
    public String updateProduct(UUID id, ProductUpsertDto dto) {
        var product = productRepository.findById(id).orElseThrow(() -> new EComException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setUnitPrice(dto.getUnitPrice());
        product.setQuantityInStock(dto.getQuantityInStock());
        productRepository.save(product);
        return product.getId().toString();
    }

    @Override
    public void deleteById(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new EComException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public List<ProductDto> search(String keySearch, Integer pageSize, Integer pageNo) {
        Pageable page = PageRequest.of(pageNo, pageSize);
        List<Product> entities;
        if (keySearch != null) {
            entities = productRepository.findByNameContainsOrDescriptionContains(keySearch, keySearch, page);
        } else {
            entities = productRepository.findAll(page).getContent().stream().toList();
        }
        return entities
                .stream()
                .map(product -> ProductDto
                        .builder()
                        .id(product.getId())
                        .description(product.getDescription())
                        .name(product.getName())
                        .quantityInStock(product.getQuantityInStock())
                        .unitPrice(product.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
