package com.logitrack.orderservice.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.orderservice.dtos.producer.InventoryServiceDtoProducer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class InventoryServiceDtoSerializer implements Serializer<InventoryServiceDtoProducer> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, InventoryServiceDtoProducer inventoryServiceDtoProducer) {
        try {
            return  objectMapper.writeValueAsBytes(inventoryServiceDtoProducer);
        } catch (Exception ex) {
            throw new SerializationException("Error serializing InventoryServiceDto", ex);
        }
    }
}
