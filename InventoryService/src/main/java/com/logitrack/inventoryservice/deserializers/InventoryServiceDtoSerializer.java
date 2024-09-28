package com.logitrack.inventoryservice.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.inventoryservice.dtos.InventoryServiceDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class InventoryServiceDtoSerializer implements Deserializer<InventoryServiceDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public InventoryServiceDto deserialize(String s, byte[] data) {
        try {
            return objectMapper.readValue(data, InventoryServiceDto.class);
        } catch (Exception ex) {
            throw  new SerializationException("Ошибка при десериализации InventoryServiceDto", ex);
        }
    }
}