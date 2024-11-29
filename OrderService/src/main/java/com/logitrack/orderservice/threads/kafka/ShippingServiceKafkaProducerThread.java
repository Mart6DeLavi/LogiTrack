package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.ShippingServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.producer.ShippingServiceDtoProducer;
import com.logitrack.orderservice.exceptions.kafka.ShippingServiceKafkaNotSentException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class ShippingServiceKafkaProducerThread {

    private ExecutorService executorService;

    private final ShippingServiceKafkaProducer producer;

    private static final String SHIPPING_TOPIC = "order-service-to-shipping-service";

    @PostConstruct
    public void init() {
        executorService = new ThreadPoolExecutor(0, 5, 60L,
                TimeUnit.SECONDS, new SynchronousQueue<>(), r -> {
            Thread thread = new Thread(r);
            thread.setName("ShippingServiceKafkaProducerThread-" + thread.getId());
            thread.setDaemon(true);
            return thread;
        });
    }

    @Async
    public void sendToShippingService(OrderEntity orderEntity) {
        executorService.submit(() -> {
           try {
               ShippingServiceDtoProducer shippingServiceDtoProducer = new ShippingServiceDtoProducer();

               shippingServiceDtoProducer.setOrderNumber(orderEntity.getOrderNumber());
               shippingServiceDtoProducer.setProductId(orderEntity.getProductId());
               shippingServiceDtoProducer.setProductName(orderEntity.getProductName());
               shippingServiceDtoProducer.setProductPrice(orderEntity.getPrice());

               producer.sendToShippingService(SHIPPING_TOPIC, shippingServiceDtoProducer);
           } catch (RuntimeException ex) {
               log.error("Failed to send to shipping service: {}", ex.getMessage());
               throw new ShippingServiceKafkaNotSentException(ex.getMessage(), ex);
           }
        });
    }

    @PreDestroy
    public void shutdownExecutor() {
        if (executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                    executorService.shutdown();
                }
            }
            catch (InterruptedException ex) {
                executorService.shutdown();
            }
        }
    }
}
