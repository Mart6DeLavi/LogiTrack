package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.NotificationServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.producer.NotificationServiceDtoProducer;
import com.logitrack.orderservice.exceptions.kafka.NotificationServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * Поток {@code NotificationServiceKafkaProducerThread} предназначен для отправки сообщений о заказах в сервис уведомлений
 * через Kafka. Этот поток работает асинхронно, чтобы не блокировать основной поток выполнения.
 *
 * <p>Поток использует {@link NotificationServiceKafkaProducer} для отправки сообщений и обрабатывает исключения,
 * связанные с отправкой, логируя их и выбрасывая пользовательское исключение {@link NotificationServiceKafkaNotSentException}.</p>
 */
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceKafkaProducerThread extends Thread {

    private final NotificationServiceKafkaProducer producer;

    private static final String NOTIFICATION_TOPIC = "order-service-to-notification-service";


    @Async
    public void sendToNotificationService(OrderEntity orderEntity) {
        Thread notificationServiceKafkaProducerThread = new Thread(() -> {
           NotificationServiceDtoProducer notificationServiceDtoProducer = new NotificationServiceDtoProducer();

           notificationServiceDtoProducer.setProduct_name(orderEntity.getProductName());
           notificationServiceDtoProducer.setOrder_number(orderEntity.getOrderNumber());

           try {
               producer.sendToNotificationService(NOTIFICATION_TOPIC, notificationServiceDtoProducer);
           } catch (RuntimeException ex) {
               throw new NotificationServiceKafkaNotSentException(ex.getMessage(), ex);
           }
        });

        notificationServiceKafkaProducerThread.start();
    }
}
