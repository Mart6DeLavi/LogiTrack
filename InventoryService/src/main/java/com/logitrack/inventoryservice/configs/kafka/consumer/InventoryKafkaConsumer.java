package com.logitrack.inventoryservice.configs.kafka.consumer;

import com.logitrack.inventoryservice.dtos.InventoryServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InventoryKafkaConsumer {

    @KafkaListener(topics = "order-service-to-inventory-service", groupId = "order-service")
    public void consume(InventoryServiceDto inventoryServiceDto) {

        log.info("Consumed inventory service dto: {}", inventoryServiceDto.getProduct_id());
    }
}
