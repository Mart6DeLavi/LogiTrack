package com.logitrack.orderservice.exceptions.kafka;

/**
 * Исключение {@code CustomerServiceKafkaNotSentException} возникает, когда происходит ошибка при отправке сообщения
 * в Kafka для сервиса клиента.
 *
 * <p>Это исключение используется для указания на то, что сообщение не удалось отправить в Kafka из-за различных
 * проблем, таких как сбои в сети, ошибки конфигурации или проблемы с самим Kafka.</p>
 *
 * <p>Класс расширяет {@link RuntimeException} и предоставляет два конструктора: один для передачи сообщения об ошибке,
 * а другой для передачи сообщения об ошибке вместе с причиной (другим исключением).</p>
 *
 * @see RuntimeException
 */
public class CustomerServiceKafkaNotSentException extends RuntimeException {
    /**
     * Создает новое исключение {@code CustomerServiceKafkaNotSentException} с указанным сообщением.
     *
     * @param message Сообщение, которое будет отображаться при возникновении исключения.
     */
    public CustomerServiceKafkaNotSentException(String message) {
        super(message);
    }

    /**
     * Создает новое исключение {@code CustomerServiceKafkaNotSentException} с указанным сообщением и причиной.
     *
     * @param message Сообщение, которое будет отображаться при возникновении исключения.
     * @param cause   Причина возникновения исключения (другое исключение, которое вызвало это исключение).
     */
    public CustomerServiceKafkaNotSentException(String message,
                                                Throwable cause) {
        super(message, cause);
    }
}