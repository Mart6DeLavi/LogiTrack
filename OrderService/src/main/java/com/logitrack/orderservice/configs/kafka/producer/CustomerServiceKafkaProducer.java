package com.logitrack.orderservice.configs.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * {@code CustomerServiceKafkaProducer} является сервисом для отправки сообщений в Kafka.
 *
 * <p>Этот класс использует {@link KafkaTemplate} для отправки сообщений в указанный топик Kafka. Также предоставляется
 * логирование отправленных сообщений для удобства отслеживания и диагностики.</p>
 *
 * <p>Класс помечен аннотацией {@link Service}, что позволяет Spring распознавать его как компонент сервиса.
 * Аннотация {@link RequiredArgsConstructor} автоматически генерирует конструктор для финальных полей,
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
public class CustomerServiceKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Отправляет сообщение в указанный Kafka-топик.
     *
     * <p>Метод использует {@link KafkaTemplate} для отправки сообщения в указанный топик. После отправки сообщения
     * производится логирование, чтобы отслеживать, какие сообщения были отправлены и в какие топики.</p>
     *
     * @param topic   Название Kafka-топика, в который будет отправлено сообщение.
     * @param message Сообщение, которое необходимо отправить в Kafka. Оно приводится к строке перед отправкой.
     * @throws ClassCastException если {@code message} не является строкой.
     */
    public void sendToCustomerService(String topic,
                                      Object message) {
        kafkaTemplate.send(topic, message);
        log.info("Sent: {} to topic: {}", message, topic);
    }
}
