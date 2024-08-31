package com.logitrack.orderservice.exceptions.kafka;

public class InventoryServiceKafkaNotSentException extends RuntimeException {
    public InventoryServiceKafkaNotSentException(String message) {
        super(message);
    }

    public InventoryServiceKafkaNotSentException(String message, Throwable cause) { super(message, cause); }
}
