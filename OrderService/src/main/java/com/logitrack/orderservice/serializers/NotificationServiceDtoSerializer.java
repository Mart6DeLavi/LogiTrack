package com.logitrack.orderservice.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.orderservice.dtos.producer.NotificationServiceDtoProducer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class NotificationServiceDtoSerializer implements Serializer<NotificationServiceDtoProducer> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, NotificationServiceDtoProducer data) {
        try {
            return  objectMapper.writeValueAsBytes(data);
        } catch (Exception ex) {
            throw new SerializationException("Error serializing InventoryServiceDto", ex);
        }
    }
}
