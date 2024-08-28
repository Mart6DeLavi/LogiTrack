package com.logitrack.orderservice.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_second_name", nullable = false)
    private String clientSecondName;

    @Column(name = "client_phone", nullable = false)
    private String clientPhone;

    @Column(name = "client_email", nullable = false)
    private String clientEmail;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "estimated_delivery_time", nullable = false)
    private LocalDateTime estimatedDeliveryTime;

    @Column(name = "client_address", nullable = false)
    private String clientAddress;

    @Column(name = "sending_address", nullable = false)
    private String sendingAddress;

    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @Column(name = "price", nullable = false)
    private double price;
}
