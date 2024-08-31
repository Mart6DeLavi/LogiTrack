package com.logitrack.orderservice.configs.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * {@code InventoryServiceKafkaProducer} представляет собой сервис для отправки сообщений в Kafka.
 *
 * <p>Этот класс использует {@link KafkaTemplate} для отправки сообщений в указанный топик Kafka.
 * После отправки сообщения производится логирование, чтобы отслеживать, какие сообщения были отправлены и в какие топики.</p>
 *
 * <p>Класс помечен аннотацией {@link Service}, что позволяет Spring распознавать его как компонент сервиса.
 * Аннотация {@link RequiredArgsConstructor} автоматически генерирует конструктор для финального поля {@code kafkaTemplate},
 * а {@link Slf4j} предоставляет поддержку для логирования.</p>
 *
 * @see org.springframework.kafka.core.KafkaTemplate
 * @see org.springframework.stereotype.Service
 * @see lombok.RequiredArgsConstructor
 * @see lombok.extern.slf4j.Slf4j
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Отправляет сообщение в указанный Kafka-топик.
     *
     * <p>Метод использует {@link KafkaTemplate} для отправки сообщения в указанный топик. После отправки сообщения
     * производится логирование, чтобы отслеживать, какие сообщения были отправлены и в какие топики.</p>
     *
     * <p>Обратите внимание, что {@code message} приводится к строке перед отправкой. Если {@code message} не является
     * строкой, это может привести к {@link ClassCastException}.</p>
     *
     * @param topic   Название Kafka-топика, в который будет отправлено сообщение.
     * @param message Сообщение, которое необходимо отправить в Kafka. Оно приводится к строке перед отправкой.
     *
     * @throws ClassCastException если {@code message} не может быть приведено к строке.
     */
    public void sendToInventoryService(String topic,
                                       Object message) {
        kafkaTemplate.send(topic, (String) message);
        log.info("Sent: {} to topic: {}", message, topic);
    }
}
