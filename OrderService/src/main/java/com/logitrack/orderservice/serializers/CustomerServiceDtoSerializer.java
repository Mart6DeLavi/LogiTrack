package com.logitrack.orderservice.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.orderservice.dtos.CustomerServiceDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class CustomerServiceDtoSerializer implements Serializer<CustomerServiceDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, CustomerServiceDto data) {
        try {
            return  objectMapper.writeValueAsBytes(data);
        } catch (Exception ex) {
            throw new SerializationException("Error serializing CustomerServiceDto", ex);
        }
    }
}
