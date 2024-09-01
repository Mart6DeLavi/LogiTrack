package com.logitrack.inventoryservice.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class ProductEntity {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "productName", nullable = false)
    private String productName;

    @Column(name = "productId", nullable = false)
    private int productId;

    @Column(name = "price", nullable = false)
    private double price;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ProductEntity that)) return false;
        return getId() == that.getId() && getProductId() == that.getProductId() && Double.compare(getPrice(), that.getPrice()) == 0 && Objects.equals(getProductName(), that.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductName(), getProductId(), getPrice());
    }
}
