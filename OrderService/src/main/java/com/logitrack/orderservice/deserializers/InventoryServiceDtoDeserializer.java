package com.logitrack.orderservice.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.orderservice.dtos.consumer.InventoryServiceDtoConsumer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class InventoryServiceDtoDeserializer implements Deserializer<InventoryServiceDtoConsumer> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public InventoryServiceDtoConsumer deserialize(String topic, byte[] data) {
        try {
            if (data == null || data.length == 0) {
                return null;
            }
            return objectMapper.readValue(data, InventoryServiceDtoConsumer.class);
        } catch (Exception ex) {
            throw new SerializationException("Error deserializing data", ex);
        }
    }

    @Override
    public void close() {}
}
