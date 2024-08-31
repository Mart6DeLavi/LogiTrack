package com.logitrack.orderservice.exceptions.kafka;

public class PaymentsServiceKafkaNotSentException extends RuntimeException {
    public PaymentsServiceKafkaNotSentException(String message) {
        super(message);
    }

    public PaymentsServiceKafkaNotSentException(String message, Throwable cause) { super(message, cause); }
}
