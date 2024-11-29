package com.logitrack.orderservice.configs.kafka.producer;

import com.logitrack.orderservice.dtos.producer.PaymentsServiceDtoProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * {@code PaymentsServiceKafkaProducer} представляет собой сервис для отправки сообщений в Kafka.
 *
 * <p>Этот класс использует {@link KafkaTemplate} для отправки сообщений в указанный топик Kafka. После отправки сообщения
 * производится логирование, чтобы отслеживать, какие сообщения были отправлены и в какие топики.</p>
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
@Slf4j
@Service
public class PaymentsServiceKafkaProducer implements KafkaProducer<PaymentsServiceDtoProducer> {
    private final KafkaTemplate<String, PaymentsServiceDtoProducer> kafkaTemplate;

    public PaymentsServiceKafkaProducer(KafkaTemplate<String, PaymentsServiceDtoProducer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendToKafka(String topic, PaymentsServiceDtoProducer value) {
        kafkaTemplate.send(topic, value);
        log.info("Sent to Kafka topic {}", topic);
    }
}
