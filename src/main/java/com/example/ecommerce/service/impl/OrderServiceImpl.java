package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.OrderDetailUpsertDto;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.dto.OrderUpsertDto;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.exception.EComException;
import com.example.ecommerce.exception.ErrorCode;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.utils.EComModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderDto findById(UUID id) {
        return orderRepository.findById(id)
                .map(order -> EComModelMapper.getInstance().map(order, OrderDto.class))
                .orElseThrow(() -> new EComException(ErrorCode.ORDER_NOT_FOUND));
    }

    @Override
    public String createOrder(OrderUpsertDto dto) {
        Order order = new Order();
        order.setCustomerName(dto.getCustomerName());
        order.setCustomerEmail(dto.getCustomerEmail());
        order.setPhoneNumber(dto.getPhoneNumber());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setStatus(dto.getStatus());

        var orderDetails = dto.getOrderDetails().stream().map(orderDetailUpsertDto -> {
            var product = productRepository.findById(UUID.fromString(orderDetailUpsertDto.getProductId()))
                    .orElseThrow(() -> new EComException(ErrorCode.PRODUCT_NOT_FOUND));
            if (product.getQuantityInStock() < orderDetailUpsertDto.getQuantity()) {
                throw new EComException(ErrorCode.PRODUCT_QUANTITY_NOT_ENOUGH);
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(product.getUnitPrice());
            orderDetail.setProductId(product.getId().toString());
            orderDetail.setProductName(product.getName());
            orderDetail.setProductDescription(product.getDescription());
            orderDetail.setCustomerNote(orderDetailUpsertDto.getCustomerNote());
            orderDetail.setQuantity(orderDetailUpsertDto.getQuantity());
            orderDetail.setTotal(product.getUnitPrice().multiply(BigDecimal.valueOf(orderDetailUpsertDto.getQuantity())));
            orderDetail.setOrder(order);
            return orderDetail;

        }).collect(Collectors.toList());

        order.setOrderDetails(orderDetails);
        BigDecimal totalPrice = order.getOrderDetails().stream().map(OrderDetail::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return order.getId().toString();
    }

    @Override
    public String updateOrder(UUID id, OrderUpsertDto dto) {
        var order = orderRepository.findById(id).orElseThrow(() -> new EComException(ErrorCode.ORDER_NOT_FOUND));
        order.setCustomerName(dto.getCustomerName());
        order.setCustomerEmail(dto.getCustomerEmail());
        order.setPhoneNumber(dto.getPhoneNumber());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setStatus(dto.getStatus());

        var productIdsUpdate = dto.getOrderDetails().stream().map(OrderDetailUpsertDto::getProductId).collect(Collectors.toList());

        order.getOrderDetails().removeIf(orderDetail -> !productIdsUpdate.contains(orderDetail.getProductId()));

        var productIdsCurrent = order.getOrderDetails().stream().map(OrderDetail::getProductId).collect(Collectors.toList());

        dto.getOrderDetails().forEach(orderDetailUpsertDto -> {
            var productId = orderDetailUpsertDto.getProductId();
            var product = productRepository.findById(UUID.fromString(orderDetailUpsertDto.getProductId()))
                    .orElseThrow(() -> new EComException(ErrorCode.PRODUCT_NOT_FOUND));
            if (product.getQuantityInStock() < orderDetailUpsertDto.getQuantity()) {
                throw new EComException(ErrorCode.PRODUCT_QUANTITY_NOT_ENOUGH);
            }

            if (productIdsCurrent.contains(productId)) {
                var orderDetail = order.getOrderDetails().stream().filter(o -> o.getProductId().equals(productId)).findFirst().orElseThrow();
                orderDetail.setCustomerNote(orderDetailUpsertDto.getCustomerNote());
                orderDetail.setQuantity(orderDetailUpsertDto.getQuantity());
                orderDetail.setTotal(product.getUnitPrice().multiply(BigDecimal.valueOf(orderDetailUpsertDto.getQuantity())));
            } else {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setPrice(product.getUnitPrice());
                orderDetail.setProductId(product.getId().toString());
                orderDetail.setProductName(product.getName());
                orderDetail.setProductDescription(product.getDescription());
                orderDetail.setCustomerNote(orderDetailUpsertDto.getCustomerNote());
                orderDetail.setQuantity(orderDetailUpsertDto.getQuantity());
                orderDetail.setTotal(product.getUnitPrice().multiply(BigDecimal.valueOf(orderDetailUpsertDto.getQuantity())));
                orderDetail.setOrder(order);
                order.getOrderDetails().add(orderDetail);
            }
        });

        BigDecimal totalPrice = order.getOrderDetails().stream().map(OrderDetail::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        return id.toString();
    }

    @Override
    public List<OrderDto> findByCustomerName(String customerName, UUID orderId, Integer pageSize, Integer pageNo) {
        if (orderId != null) {
            return Collections.singletonList(this.findById(orderId));
        }

        if (StringUtils.isEmpty(customerName)) {
            throw new EComException(ErrorCode.ORDER_CUSTOMER_NAME_INVALID);
        }
        Pageable page = PageRequest.of(pageNo, pageSize);
        return orderRepository.findByCustomerName(customerName, page).stream()
                .map(order -> EComModelMapper.getInstance().map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(order -> EComModelMapper.getInstance().map(order, OrderDto.class))
                .collect(Collectors.toList());
    }
}
