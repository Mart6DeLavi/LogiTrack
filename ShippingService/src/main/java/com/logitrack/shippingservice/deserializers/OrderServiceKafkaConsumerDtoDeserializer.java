package com.logitrack.shippingservice.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.shippingservice.dtos.consumer.OrderServiceKafkaConsumerDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class OrderServiceKafkaConsumerDtoDeserializer implements Deserializer<OrderServiceKafkaConsumerDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public OrderServiceKafkaConsumerDto deserialize(String topic, byte[] data) {
        try {
            if (data == null || data.length == 0) {
                return null;
            }
            return objectMapper.readValue(data, OrderServiceKafkaConsumerDto.class);
        } catch (Exception ex) {
            throw new SerializationException("Error deserializing data", ex);
        }
    }

    @Override
    public void close() {}
}
