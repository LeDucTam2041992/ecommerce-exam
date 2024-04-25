package com.example.ecommerce.api;

import com.example.ecommerce.dto.ProductUpsertDto;
import com.example.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApis {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> apiProductsIdGet(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Object> apiProductsPost(@Valid @RequestBody ProductUpsertDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> apiProductsIdPut(@PathVariable UUID id, @Valid @RequestBody ProductUpsertDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apiProductsIdDelete(@PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Object> apiProductsGet(
            @Valid @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @Valid @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @Valid @RequestParam(value = "keySearch", required = false) String keySearch
    ) {
        return ResponseEntity.ok(productService.search(keySearch, pageSize, pageNo));
    }}
