package com.logitrack.orderservice.exceptions;

public class NoSuchOrderException extends RuntimeException {
    public NoSuchOrderException(String order_number) {
        super(String.format("No such order: %s", order_number));
    }
}
