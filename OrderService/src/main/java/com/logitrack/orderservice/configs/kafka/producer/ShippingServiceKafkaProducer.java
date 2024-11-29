package com.logitrack.orderservice.configs.kafka.producer;

import com.logitrack.orderservice.dtos.producer.ShippingServiceDtoProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShippingServiceKafkaProducer implements KafkaProducer<ShippingServiceDtoProducer> {
    private final KafkaTemplate<String, ShippingServiceDtoProducer> kafkaTemplate;

    public ShippingServiceKafkaProducer(KafkaTemplate<String, ShippingServiceDtoProducer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendToKafka(String topic, ShippingServiceDtoProducer value) {
        kafkaTemplate.send(topic, value);
        log.info("Sent to Kafka topic {}", topic);
    }
}
