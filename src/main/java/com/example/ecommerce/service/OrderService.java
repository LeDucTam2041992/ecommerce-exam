package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.dto.OrderUpsertDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto findById(UUID id);

    String createOrder(OrderUpsertDto dto);

    String updateOrder(UUID id, OrderUpsertDto dto);

    List<OrderDto> findByCustomerName(String customerName, UUID orderId, Integer pageSize, Integer pageNo);

    List<OrderDto> getAll();
}
