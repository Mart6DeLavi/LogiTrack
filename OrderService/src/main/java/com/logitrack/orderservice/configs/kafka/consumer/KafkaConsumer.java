package com.logitrack.orderservice.configs.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "auth-service")
    public void listen(String message) {
        System.out.println("Received token: " + message);
    }
}
