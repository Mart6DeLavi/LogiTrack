package com.logitrack.inventoryservice.threads.kafka;


import com.logitrack.inventoryservice.configs.kafka.producer.OrderServiceKafkaProducer;
import com.logitrack.inventoryservice.dtos.OrderServiceDto;
import com.logitrack.inventoryservice.entities.Product;
import com.logitrack.inventoryservice.exceptions.NoSendMessageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class OrderServiceKafkaProducerThread {

    private final OrderServiceKafkaProducer producer;

    private static final String ORDER_TOPIC = "inventory-service-to-order-service";

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
            } catch (RuntimeException ex) {
                throw new NoSendMessageException(ex.getMessage());
            }
            log.info("Order Service Kafka Producer Thread Finished");
        });

        sendToOrderServiceThread.start();
    }
}
