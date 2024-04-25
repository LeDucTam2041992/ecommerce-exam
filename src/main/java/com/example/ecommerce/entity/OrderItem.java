//package com.example.ecommerce.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.UuidGenerator;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//@Entity
//@Table(name = "order_item")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class OrderItem {
//    @Id
//    @UuidGenerator
//    private UUID id;
//
//    @NotNull
//    @Column(name = "product_code")
//    private String productCode;
//
//    @Column(name = "product_name")
//    private String productName;
//
//    @NotNull
//    @Column(name = "provider_code")
//    private String providerCode;
//
//    @Column(name = "price", precision = 21, scale = 2)
//    private BigDecimal price = BigDecimal.ZERO;
//
//    @NotNull
//    @Column(name = "quantity")
//    private Integer quantity = 1;
//
//    @NotNull
//    @Column(name = "sub_total", precision = 21, scale = 2)
//    private BigDecimal subTotal = BigDecimal.ZERO;
//
//    @ManyToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "order_detail_id", referencedColumnName = "id")
//    private OrderDetail orderDetail;
//
//}
