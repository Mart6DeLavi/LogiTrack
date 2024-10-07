package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.InventoryServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.producer.InventoryServiceDtoProducer;
import com.logitrack.orderservice.exceptions.kafka.InventoryServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Поток {@code InventoryServiceKafkaProducerThread} предназначен для отправки сообщений о заказах в сервис инвентаризации
 * через Kafka. Этот поток позволяет обрабатывать отправку сообщений асинхронно, чтобы не блокировать основной поток выполнения.
 *
 * <p>Поток использует {@link InventoryServiceKafkaProducer} для отправки сообщений и обрабатывает исключения,
 * связанные с отправкой, логируя их и выбрасывая пользовательское исключение {@link InventoryServiceKafkaNotSentException}.</p>
 */
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class InventoryServiceKafkaProducerThread {

    private final InventoryServiceKafkaProducer producer;

    private static final String INVENTORY_TOPIC = "order-service-to-inventory-service";

    @Async
    public void sendToInventoryService(OrderEntity orderEntity) {
        Thread inventoryServiceKafkaProducerThread = new Thread(() -> {
            InventoryServiceDtoProducer inventoryServiceDtoProducer = new InventoryServiceDtoProducer();
            inventoryServiceDtoProducer.setProduct_id(orderEntity.getProductId());
           try {
               producer.sendToInventoryService(INVENTORY_TOPIC, inventoryServiceDtoProducer);
           } catch (RuntimeException ex) {
               throw new InventoryServiceKafkaNotSentException(ex.getMessage());
           }
        });

        inventoryServiceKafkaProducerThread.start();
    }
}
