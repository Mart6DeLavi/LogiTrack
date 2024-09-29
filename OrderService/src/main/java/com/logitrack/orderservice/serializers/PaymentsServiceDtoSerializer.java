package com.logitrack.orderservice.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitrack.orderservice.dtos.NotificationServiceDto;
import com.logitrack.orderservice.dtos.PaymentsServiceDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class PaymentsServiceDtoSerializer implements Serializer<PaymentsServiceDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, PaymentsServiceDto data) {
        try {
            return  objectMapper.writeValueAsBytes(data);
        } catch (Exception ex) {
            throw new SerializationException("Error serializing InventoryServiceDto", ex);
        }
    }
}
