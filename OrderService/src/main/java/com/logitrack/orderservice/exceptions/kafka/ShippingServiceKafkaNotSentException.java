package com.logitrack.orderservice.exceptions.kafka;

public class ShippingServiceKafkaNotSentException extends RuntimeException {
    public ShippingServiceKafkaNotSentException(String message) {
        super(message);
    }

    public ShippingServiceKafkaNotSentException(String message, Throwable cause) {
      super(message, cause);
    }
}
