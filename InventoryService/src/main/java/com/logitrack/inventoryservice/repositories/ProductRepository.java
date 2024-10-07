package com.logitrack.inventoryservice.repositories;

import com.logitrack.inventoryservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT product FROM Product product WHERE product.id = :productId")
    Optional<Product> findProductById(@Param("productId") int productId);
}
