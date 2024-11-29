package com.logitrack.orderservice.mapps;

import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.producer.CustomerServiceDtoProducer;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerServiceDtoMapper implements DtoMapper<CustomerServiceDtoProducer> {
    @Override
    public CustomerServiceDtoProducer map(OrderEntity orderEntity) {
        return Optional.ofNullable(orderEntity)
                .map(order -> {
                    CustomerServiceDtoProducer dto = new CustomerServiceDtoProducer();
                    dto.setOrder_number(order.getOrderNumber());
                    dto.setOrder_time(order.getOrderTime());
                    dto.setEstimated_delivery_time(order.getEstimatedDeliveryTime());
                    dto.setDelivery_address(order.getClientAddress());
                    dto.setOrder_number(order.getOrderNumber());
                    dto.setPrice(order.getPrice());
                    return dto;
                })
                .orElseThrow(()-> new IllegalArgumentException("Order Entity is null"));
    }
}
