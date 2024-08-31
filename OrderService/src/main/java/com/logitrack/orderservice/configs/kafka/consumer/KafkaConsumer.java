package com.logitrack.orderservice.configs.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * {@code KafkaConsumer} представляет собой компонент Spring, который слушает сообщения из Kafka-топика и обрабатывает их.
 *
 * <p>Этот сервис использует аннотацию {@link KafkaListener} для прослушивания сообщений, поступающих из топика {@code "auth-service"}.
 * Когда сообщение получено, оно обрабатывается методом {@link #listen(String)}, который в данный момент просто выводит сообщение на консоль.
 * </p>
 *
 * <p>Класс {@code KafkaConsumer} предназначен для демонстрации базовой функциональности прослушивания сообщений из Kafka.
 * Он может быть расширен для выполнения более сложной обработки сообщений в зависимости от требований вашего приложения.</p>
 *
 * @see org.springframework.kafka.annotation.KafkaListener
 * @see org.springframework.stereotype.Service
 */
@Service
public class KafkaConsumer {
    /**
     * Метод {@code listen} обрабатывает входящие сообщения из Kafka-топика {@code "auth-service"}.
     *
     * <p>Когда сообщение поступает из указанного топика, оно передается в этот метод в виде строки.
     * В текущей реализации метод выводит полученное сообщение на консоль. Вы можете заменить эту логику на более сложную обработку
     * в зависимости от требований вашего приложения.</p>
     *
     * @param message Сообщение, полученное из Kafka-топика {@code "auth-service"}. В данном случае это строка, представляющая
     *                токен или другой текстовый контент.
     */
    @KafkaListener(topics = "auth-service")
    public void listen(String message) {
        System.out.println("Received token: " + message);
    }
}
