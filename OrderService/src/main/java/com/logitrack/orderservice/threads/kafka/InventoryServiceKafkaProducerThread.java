package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.InventoryServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.InventoryServiceDto;
import com.logitrack.orderservice.exceptions.kafka.InventoryServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class InventoryServiceKafkaProducerThread extends Thread {

    private final InventoryServiceKafkaProducer producer;
    private final OrderEntity orderEntity;

    private static final String KAFKA_TOPIC = "order-service-to-inventory-service";

    /**
     * Запускает поток, который отправляет сообщение о заказе в сервис инвентаризации через Kafka.
     *
     * <p>Этот метод переопределяет {@link Thread#run()} и вызывает {@link #sendMessage()} для отправки сообщения.
     * В случае возникновения исключения при отправке сообщения, оно логируется и пробрасывается как
     * {@link InventoryServiceKafkaNotSentException}.</p>
     */
    @Override
    public void run() {
        log.info("Inventory Service Thread Started");
        try {
            sendMessage();
        } catch (RuntimeException ex) {
            log.error("Error sending message to inventory service: {}", ex.getMessage());
            throw new InventoryServiceKafkaNotSentException(ex.getMessage(), ex);
        }
        log.info("Inventory Service Thread Finished");
    }

    /**
     * Создает объект {@link InventoryServiceDto} из данных заказа и отправляет его в сервис инвентаризации через Kafka.
     *
     * <p>Этот метод вызывает {@link InventoryServiceKafkaProducer#sendToInventoryService(String, InventoryServiceDto)}
     * для отправки сообщения. Если возникает исключение при отправке сообщения, оно пробрасывается как
     * {@link InventoryServiceKafkaNotSentException}.</p>
     */
    private void sendMessage() {
        InventoryServiceDto inventoryServiceDto = new InventoryServiceDto();

        inventoryServiceDto.setProduct_id(orderEntity.getProductId());
        try {
            producer.sendToInventoryService(KAFKA_TOPIC, inventoryServiceDto);
        } catch (RuntimeException e) {
            throw new InventoryServiceKafkaNotSentException(e.getMessage());
        }
    }
}
