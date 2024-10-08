package com.logitrack.inventoryservice.configs.kafka.producer;


import com.logitrack.inventoryservice.dtos.OrderServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka producer для отправки сообщений в order-service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceKafkaProducer {

    private final KafkaTemplate<String, OrderServiceDto> kafkaTemplate;

    /**
     * Отправляет сообщение в Kafka в указанный топик.
     *
     * @param topic   название топика
     * @param message сообщение для отправки
     */
    public void sendToOrderService(String topic, OrderServiceDto message) {
        kafkaTemplate.send(topic, message);
        log.info("Message sent to order service topic: " + topic);
    }
}
