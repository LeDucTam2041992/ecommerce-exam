package com.example.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private UUID id;
    private String productId;
    private String productName;
    private String productDescription;
    private String customerNote;
    private BigDecimal price = BigDecimal.ZERO;
    private Integer quantity = 1;
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal totalTax = BigDecimal.ZERO;
    private BigDecimal totalShipping = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
}
