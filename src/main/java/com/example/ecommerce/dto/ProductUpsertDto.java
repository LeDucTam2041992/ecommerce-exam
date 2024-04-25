package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpsertDto {
    private String name;
    private String description;

    @NotNull
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @NotNull
    private Integer quantityInStock = 0;
}
