package com.example.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "customer_note")
    private String customerNote;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity = 1;

    @NotNull
    @Column(name = "sub_total", precision = 21, scale = 2)
    private BigDecimal subTotal = BigDecimal.ZERO;

    @NotNull
    @Column(name = "total_tax", precision = 21, scale = 2)
    private BigDecimal totalTax = BigDecimal.ZERO;

    @NotNull
    @Column(name = "total_shipping")
    private BigDecimal totalShipping = BigDecimal.ZERO;

    @NotNull
    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

}
