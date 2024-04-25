package com.example.ecommerce.dto;

import com.example.ecommerce.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderUpsertDto {
    private String customerName;
    private String deliveryAddress;
    private String customerEmail;
    private String phoneNumber;
    private OrderStatus status;
    private List<OrderDetailUpsertDto> orderDetails;
}
