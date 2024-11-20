package com.logitrack.orderservice.exceptions.kafka;

/**
 * Исключение {@code PaymentsServiceKafkaNotSentException} возникает, когда происходит ошибка при отправке сообщения
 * в Kafka для сервиса платежей.
 *
 * <p>Это исключение используется для указания на то, что сообщение не удалось отправить в Kafka из-за различных
 * проблем, таких как сбои в сети, ошибки конфигурации или проблемы с самим Kafka.</p>
 *
 * <p>Класс расширяет {@link RuntimeException} и предоставляет два конструктора: один для передачи сообщения об ошибке,
 * а другой для передачи сообщения об ошибке вместе с причиной (другим исключением).</p>
 *
 * @see RuntimeException
 */
public class PaymentsServiceKafkaNotSentException extends RuntimeException {
    /**
     * Создает новое исключение {@code PaymentsServiceKafkaNotSentException} с указанным сообщением.
     *
     * @param message Сообщение, которое будет отображаться при возникновении исключения.
     */
    public PaymentsServiceKafkaNotSentException(String message) {
        super(message);
    }

    /**
     * Создает новое исключение {@code PaymentsServiceKafkaNotSentException} с указанным сообщением и причиной.
     *
     * @param message Сообщение, которое будет отображаться при возникновении исключения.
     * @param cause   Причина возникновения исключения (другое исключение, которое вызвало это исключение).
     */
    public PaymentsServiceKafkaNotSentException(String message,
                                                Throwable cause) {
        super(message, cause);
    }
}
