package com.logitrack.orderservice.exceptions.kafka;

public class CustomerServiceKafkaNotSentException extends RuntimeException {
    public CustomerServiceKafkaNotSentException(String message) {
        super(message);
    }

    public CustomerServiceKafkaNotSentException(String message, Throwable cause) {
        super(message, cause);
    }


}
