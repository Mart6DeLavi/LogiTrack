package com.logitrack.orderservice.configs.kafka.consumer;

import com.logitrack.orderservice.dtos.consumer.InventoryServiceDtoConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceKafkaConsumer {

    private final CompletableFuture<InventoryServiceDtoConsumer> inventoryFuture = new CompletableFuture<>();

    @KafkaListener(topics = "inventory-service-to-order-service", groupId = "inventory-service")
    public void consumeFromInventoryService(InventoryServiceDtoConsumer inventoryServiceDtoConsumer) {
        log.info("Consumed from inventory service: {}, {}, {}", inventoryServiceDtoConsumer.getTitle(),
                inventoryServiceDtoConsumer.getManufacture(),
                inventoryServiceDtoConsumer.getPrice());

        inventoryFuture.complete(inventoryServiceDtoConsumer);
    }

    public CompletableFuture<InventoryServiceDtoConsumer> getInventoryFuture() {
        return inventoryFuture;
    }
}
