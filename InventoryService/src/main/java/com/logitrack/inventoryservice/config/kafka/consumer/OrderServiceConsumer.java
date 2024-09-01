package com.logitrack.inventoryservice.config.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceConsumer {

    @KafkaListener(topics = "order-service-to-inventory-service", groupId = "order-service")
    public void listenCustomerService(String message) {
        System.out.println("Received message: " + message);
    }
}
