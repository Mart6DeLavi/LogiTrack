package com.logitrack.orderservice.mapps;

import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.producer.NotificationServiceDtoProducer;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotificationServiceDtoMapper implements DtoMapper<NotificationServiceDtoProducer> {

    @Override
    public NotificationServiceDtoProducer map(OrderEntity orderEntity) {
        return Optional.ofNullable(orderEntity)
                .map(order -> {
                    NotificationServiceDtoProducer dto = new NotificationServiceDtoProducer();
                    dto.setProduct_name(order.getProductName());
                    dto.setOrder_number(order.getOrderNumber());
                    return dto;
                })
                .orElseThrow(() ->new IllegalArgumentException("OrderEntity is null"));
    }
}
