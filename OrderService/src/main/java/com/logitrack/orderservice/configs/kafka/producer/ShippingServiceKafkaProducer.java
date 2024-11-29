package com.logitrack.orderservice.configs.kafka.producer;

import com.logitrack.orderservice.dtos.producer.ShippingServiceDtoProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingServiceKafkaProducer {

    @Autowired
    private KafkaTemplate<String, ShippingServiceDtoProducer> kafkaTemplate;

    public void sendToShippingService(String topic, ShippingServiceDtoProducer dto) {
        kafkaTemplate.send(topic, dto);
        log.info("Sent: 'shipping service dto producer' to topic: {}", topic);
    }
}
