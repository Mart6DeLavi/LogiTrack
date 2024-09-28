package com.logitrack.orderservice.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.orderservice.dtos.InventoryServiceDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class InventoryServiceDtoSerializer implements Serializer<InventoryServiceDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, InventoryServiceDto inventoryServiceDto) {
        try {
            return  objectMapper.writeValueAsBytes(inventoryServiceDto);
        } catch (Exception ex) {
            throw new SerializationException("Error serializing InventoryServiceDto", ex);
        }
    }
}
