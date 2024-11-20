package com.logitrack.inventoryservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "title",
            nullable = false,
            length = 100
    )
    private String title;

    @Column(name = "manufacture",
            nullable = false,
            length = 100
    )
    private String manufacture;

    @Column(name = "price",
            nullable = false
    )
    private int price;

    @Column(name = "received_at",
            nullable = false
    )
    private LocalDateTime receivedAt;
}
