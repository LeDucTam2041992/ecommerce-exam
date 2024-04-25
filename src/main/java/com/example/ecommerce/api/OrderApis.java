package com.example.ecommerce.api;

import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.dto.OrderUpsertDto;
import com.example.ecommerce.dto.ProductUpsertDto;
import com.example.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderApis {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> apiOrdersIdGet(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Object> apiOrdersPost(@Valid @RequestBody OrderUpsertDto dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> apiOrdersIdPut(@PathVariable UUID id, @Valid @RequestBody OrderUpsertDto dto) {
        return ResponseEntity.ok(orderService.updateOrder(id, dto));
    }

    @GetMapping
    public ResponseEntity<Object> apiOrdersFindByCustomerName(
            @Valid @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @Valid @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @Valid @RequestParam(value = "customerName", required = false) String customerName,
            @Valid @RequestParam(value = "orderId", required = false) UUID orderId
    ) {
        return ResponseEntity.ok(orderService.findByCustomerName(customerName, orderId, pageSize, pageNo));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> apiOrdersGetAll() {
        return ResponseEntity.ok(orderService.getAll());
    }
}


