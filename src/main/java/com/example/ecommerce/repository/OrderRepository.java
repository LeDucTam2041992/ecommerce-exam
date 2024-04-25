package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerName(String customerName, Pageable page);
}