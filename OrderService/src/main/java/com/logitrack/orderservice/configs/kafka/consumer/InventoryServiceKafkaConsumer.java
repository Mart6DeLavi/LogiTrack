package com.logitrack.orderservice.configs.kafka.consumer;

import com.logitrack.orderservice.dtos.consumer.InventoryServiceDtoConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceKafkaConsumer {

    @KafkaListener(topics = "inventory-service-to-order-service", groupId = "inventory-service")
    public void consumeFromInventoryService(InventoryServiceDtoConsumer inventoryServiceDtoConsumer) {
        log.info("Consumed from inventory service: {}, \n{}, \n{}", inventoryServiceDtoConsumer.getTitle(), inventoryServiceDtoConsumer.getManufacture(), inventoryServiceDtoConsumer.getPrice());
    }
}
