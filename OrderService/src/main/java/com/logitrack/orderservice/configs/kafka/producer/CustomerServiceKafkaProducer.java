package com.logitrack.orderservice.configs.kafka.producer;

import com.logitrack.orderservice.dtos.producer.CustomerServiceDtoProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CustomerServiceKafkaProducer implements KafkaProducer<CustomerServiceDtoProducer> {
    private final KafkaTemplate<String, CustomerServiceDtoProducer> kafkaTemplate;

    public CustomerServiceKafkaProducer(KafkaTemplate<String, CustomerServiceDtoProducer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendToKafka(String topic, CustomerServiceDtoProducer dto) {
        kafkaTemplate.send(topic, dto);
        log.info("Message sent to kafka topic: {}", topic);
    }
}
