package com.logitrack.orderservice.configs.kafka.producer;

import com.logitrack.orderservice.dtos.producer.InventoryServiceDtoProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class InventoryServiceKafkaProducer implements KafkaProducer<InventoryServiceDtoProducer> {
    private final KafkaTemplate<String, InventoryServiceDtoProducer> kafkaTemplate;

    public InventoryServiceKafkaProducer(KafkaTemplate<String, InventoryServiceDtoProducer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendToKafka(String topic, InventoryServiceDtoProducer dto) {
        kafkaTemplate.send(topic, dto);
        log.info("Message sent to topic: {}", topic);
    }
}
