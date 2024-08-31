package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.NotificationServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.NotificationServiceDto;
import com.logitrack.orderservice.exceptions.kafka.NotificationServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NotificationServiceKafkaProducerThread extends Thread {

    private final NotificationServiceKafkaProducer producer;
    private final OrderEntity orderEntity;

    @Override
    public void run() {
        log.info("Notification service kafka producer thread started");

        try {
            sendMessage();
        } catch (RuntimeException ex) {
            log.error("Notification service kafka producer thread failed", ex);
            throw new NotificationServiceKafkaNotSentException(ex.getMessage(), ex);
        }
    }


    private void sendMessage() {
        NotificationServiceDto notificationServiceDto = new NotificationServiceDto();

        notificationServiceDto.setProduct_name(orderEntity.getProductName());
        notificationServiceDto.setOrder_number(orderEntity.getOrderNumber());

        try {
            producer.sendToNotificationService("order-service-to-notification-service", notificationServiceDto);
        } catch (RuntimeException ex) {
            throw new NotificationServiceKafkaNotSentException(ex.getMessage());
        }
    }
}
