package com.example.ecommerce.entity;

import com.example.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "order_date")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

}
