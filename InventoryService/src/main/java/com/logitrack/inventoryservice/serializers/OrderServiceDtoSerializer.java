package com.logitrack.inventoryservice.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.inventoryservice.dtos.OrderServiceDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class OrderServiceDtoSerializer implements Serializer<OrderServiceDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, OrderServiceDto data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception ex) {
            throw new SerializationException("Error serializing order service dto", ex);
        }
    }
}
