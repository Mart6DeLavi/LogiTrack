package com.logitrack.orderservice.configs.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "auth-service")
    public void listen(String message) {
        System.out.println("Received token: " + message);
    }
}
