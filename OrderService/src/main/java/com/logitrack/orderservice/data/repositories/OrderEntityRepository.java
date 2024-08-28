package com.logitrack.orderservice.data.repositories;

import com.logitrack.orderservice.data.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findOrderEntityByOrderNumber(String orderNumber);
    void deleteByOrderNumber(String orderNumber);
}
