package com.logitrack.orderservice.exceptions.kafka;

public class NotificationServiceKafkaNotSentException extends RuntimeException {
    public NotificationServiceKafkaNotSentException(String message) {
        super(message);
    }

    public NotificationServiceKafkaNotSentException(String message, Throwable cause) { super(message, cause); }
}
