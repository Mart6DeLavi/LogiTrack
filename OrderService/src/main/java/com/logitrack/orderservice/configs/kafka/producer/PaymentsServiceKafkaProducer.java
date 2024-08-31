package com.logitrack.orderservice.configs.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentsServiceKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sentToPaymentsService(String topic,
                                      Object message) {
        kafkaTemplate.send(topic, (String) message);
        log.info("Sent: {} to topic: {}", message, topic);
    }

}
