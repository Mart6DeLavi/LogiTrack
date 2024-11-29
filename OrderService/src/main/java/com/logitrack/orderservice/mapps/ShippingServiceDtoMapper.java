package com.logitrack.orderservice.mapps;

import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.producer.ShippingServiceDtoProducer;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShippingServiceDtoMapper implements DtoMapper<ShippingServiceDtoProducer> {

    @Override
    public ShippingServiceDtoProducer map(OrderEntity orderEntity) {
        return Optional.ofNullable(orderEntity)
                .map(order -> {
                    ShippingServiceDtoProducer dtoProducer = new ShippingServiceDtoProducer();
                    dtoProducer.setProductId(order.getProductId());
                    dtoProducer.setProductName(order.getProductName());
                    dtoProducer.setOrderNumber(order.getOrderNumber());
                    dtoProducer.setProductPrice(order.getPrice());
                    return dtoProducer;
                })
                .orElseThrow(() -> new IllegalArgumentException("OrderEntity is null"));
    }
}
