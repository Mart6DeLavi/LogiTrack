package com.logitrack.orderservice.configs.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendToNotificationService(String topic,
                                          Object message) {
        kafkaTemplate.send(topic, message);
        log.info("Sent: {} to topic: {}", message, topic);
    }
}
