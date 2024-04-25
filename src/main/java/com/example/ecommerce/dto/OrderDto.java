package com.example.ecommerce.dto;

import com.example.ecommerce.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private UUID id;
    private Date orderDate;
    private String customerName;
    private String deliveryAddress;
    private String customerEmail;
    private String phoneNumber;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private List<OrderDetailDto> orderDetails;
}
