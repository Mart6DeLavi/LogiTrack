package com.logitrack.orderservice.mapps;

import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.producer.PaymentsServiceDtoProducer;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentsServiceDtoMapper implements DtoMapper<PaymentsServiceDtoProducer> {

    @Override
    public PaymentsServiceDtoProducer map(OrderEntity orderEntity) {
        return Optional.ofNullable(orderEntity)
                .map(order -> {
                    PaymentsServiceDtoProducer dtoProducer = new PaymentsServiceDtoProducer();
                    dtoProducer.setPrice(order.getPrice());
                    dtoProducer.setProduct_id(order.getProductId());
                    return dtoProducer;
                })
                .orElseThrow(() -> new IllegalArgumentException("OrderEntity is null"));
    }
}
