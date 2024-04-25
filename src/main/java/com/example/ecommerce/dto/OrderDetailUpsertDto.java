package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailUpsertDto {
    @NotNull
    private String productId;
    @NotNull
    private Integer quantity = 1;
    private String customerNote;
}
