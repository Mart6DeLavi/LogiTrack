package com.logitrack.orderservice.mapps;

import com.logitrack.orderservice.data.entities.OrderEntity;

@FunctionalInterface
public interface DtoMapper<T> {
    T map(OrderEntity orderEntity);
}
