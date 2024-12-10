package com.logitrack.shippingservice.configs.kafka.consumer;

import com.logitrack.shippingservice.dtos.consumer.OrderServiceKafkaConsumerDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceKafkaConsumer {

    @Getter
    private final CompletableFuture<OrderServiceKafkaConsumerDto> orderFuture = new CompletableFuture<>();

    @KafkaListener(topics = "order-service-to-shipping-service", groupId = "order-service", containerFactory = "orderServiceKafkaListenerContainerFactory")
    public void consumeFromOrderService(OrderServiceKafkaConsumerDto orderServiceKafkaConsumerDto) {
        log.info("Consumed from order service: {}, {}, {}, {}", orderServiceKafkaConsumerDto.getOrderNumber(),
                orderServiceKafkaConsumerDto.getProductId(),
                orderServiceKafkaConsumerDto.getProductName(),
                orderServiceKafkaConsumerDto.getProductPrice());
        orderFuture.complete(orderServiceKafkaConsumerDto);
    }
}
