package com.logitrack.orderservice.serializers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.orderservice.dtos.producer.ShippingServiceDtoProducer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class ShippingServiceDtoSerializer implements Serializer<ShippingServiceDtoProducer> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, ShippingServiceDtoProducer shippingServiceDtoProducer) {
        try {
            return objectMapper.writeValueAsBytes(shippingServiceDtoProducer);
        } catch (Exception ex) {
            throw new SerializationException("Error serializing ShippingServiceDtoProducer to JSON", ex);
        }
    }
}
