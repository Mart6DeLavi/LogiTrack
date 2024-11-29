package com.logitrack.orderservice.mapps;

import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.UserOrderCreationDto;
import com.logitrack.orderservice.dtos.producer.InventoryServiceDtoProducer;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InventoryServiceDtoMapper implements DtoMapper<InventoryServiceDtoProducer> {

    public InventoryServiceDtoProducer map(UserOrderCreationDto userOrderCreationDto) {
        InventoryServiceDtoProducer dtoProducer = new InventoryServiceDtoProducer();
        dtoProducer.setProduct_id(userOrderCreationDto.getProductId());
        return dtoProducer;
    }

    @Override
    public InventoryServiceDtoProducer map(OrderEntity orderEntity) {
        throw new UnsupportedOperationException("This mapper requires UserOrderCreationDto as an additional parameter.");
    }
}
