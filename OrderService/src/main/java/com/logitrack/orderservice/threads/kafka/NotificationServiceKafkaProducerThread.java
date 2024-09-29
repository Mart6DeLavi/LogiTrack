package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.NotificationServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.NotificationServiceDto;
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

//    /**
//     * Запускает поток, который отправляет сообщение о заказе в сервис уведомлений через Kafka.
//     *
//     * <p>Этот метод переопределяет {@link Thread#run()} и вызывает {@link #sendMessage()} для отправки сообщения.
//     * В случае возникновения исключения при отправке сообщения, оно логируется и пробрасывается как
//     * {@link NotificationServiceKafkaNotSentException}.</p>
//     */
//    @Override
//    public void run() {
//        log.info("Notification service kafka producer thread started");
//
//        try {
//            sendMessage();
//        } catch (RuntimeException ex) {
//            log.error("Notification service kafka producer thread failed", ex);
//            throw new NotificationServiceKafkaNotSentException(ex.getMessage(), ex);
//        }
//
//        log.info("Notification Service Finished");
//    }
//
//    /**
//     * Создает объект {@link NotificationServiceDto} из данных заказа и отправляет его в сервис уведомлений через Kafka.
//     *
//     * <p>Этот метод вызывает {@link NotificationServiceKafkaProducer#sendToNotificationService(String, NotificationServiceDto)}
//     * для отправки сообщения. Если возникает исключение при отправке сообщения, оно пробрасывается как
//     * {@link NotificationServiceKafkaNotSentException}.</p>
//     */
//    private void sendMessage() {
//        NotificationServiceDto notificationServiceDto = new NotificationServiceDto();
//
//        notificationServiceDto.setProduct_name(orderEntity.getProductName());
//        notificationServiceDto.setOrder_number(orderEntity.getOrderNumber());
//
//        try {
//            producer.sendToNotificationService("order-service-to-notification-service", notificationServiceDto);
//        } catch (RuntimeException ex) {
//            throw new NotificationServiceKafkaNotSentException(ex.getMessage());
//        }
//    }

    @Async
    public void sendToNotificationService(OrderEntity orderEntity) {
        Thread notificationServiceKafkaProducerThread = new Thread(() -> {
           NotificationServiceDto notificationServiceDto = new NotificationServiceDto();

           notificationServiceDto.setProduct_name(orderEntity.getProductName());
           notificationServiceDto.setOrder_number(orderEntity.getOrderNumber());

           try {
               producer.sendToNotificationService(NOTIFICATION_TOPIC, notificationServiceDto);
           } catch (RuntimeException ex) {
               throw new NotificationServiceKafkaNotSentException(ex.getMessage(), ex);
           }
        });

        notificationServiceKafkaProducerThread.start();
    }
}
