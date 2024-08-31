package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.InventoryServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.InventoryServiceDto;
import com.logitrack.orderservice.exceptions.kafka.InventoryServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class InventoryServiceKafkaProducerThread extends Thread{

    private final InventoryServiceKafkaProducer producer;
    private final OrderEntity orderEntity;

    @Override
    public void run() {
        log.info("Inventory Service Thread Started");
        try {
            sendMessage();
        } catch (RuntimeException ex) {
            log.error("Error sending message to inventory service: {}", ex.getMessage());
            throw new InventoryServiceKafkaNotSentException(ex.getMessage(), ex);
        }
    }

    private void sendMessage() {
        InventoryServiceDto inventoryServiceDto = new InventoryServiceDto();

        inventoryServiceDto.setProduct_id(orderEntity.getProductId());
        try {
            producer.sendToInventoryService("order-service-to-inventory-service", inventoryServiceDto);
        }  catch (RuntimeException e) {
            throw new InventoryServiceKafkaNotSentException(e.getMessage());
        }
    }
}
