package com.logitrack.inventoryservice.threads.kafka;


import com.logitrack.inventoryservice.configs.kafka.producer.OrderServiceKafkaProducer;
import com.logitrack.inventoryservice.dtos.OrderServiceDto;
import com.logitrack.inventoryservice.entities.Product;
import com.logitrack.inventoryservice.exceptions.NoSendMessageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Поток для отправки данных в order-service.
 * Использует асинхронное выполнение задач.
 */
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class OrderServiceKafkaProducerThread {

    private final OrderServiceKafkaProducer producer;

    private static final String ORDER_TOPIC = "inventory-service-to-order-service";

    /**
     * Асинхронная отправка данных продукта в order-service через Kafka.
     *
     * @param product данные продукта
     */

    @Async
    public void sendToOrderService(Product product) {
        Thread sendToOrderServiceThread = new Thread(() -> {
            log.info("Order Service Kafka Producer Thread Started");
            OrderServiceDto orderServiceDto = new OrderServiceDto();
            orderServiceDto.setTitle(product.getTitle());
            orderServiceDto.setManufacture(product.getManufacture());
            orderServiceDto.setPrice(product.getPrice());

            try {
                producer.sendToOrderService(ORDER_TOPIC, orderServiceDto);
                log.info("Sent to Order Service: {} \t{} \t{}", orderServiceDto.getTitle(), orderServiceDto.getManufacture(), orderServiceDto.getPrice());
            } catch (RuntimeException ex) {
                throw new NoSendMessageException(ex.getMessage());
            }
            log.info("Order Service Kafka Producer Thread Finished");
        });

        sendToOrderServiceThread.start();
    }
}
