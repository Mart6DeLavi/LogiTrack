package com.logitrack.inventoryservice.exceptions;

public class NoSendMessageException extends RuntimeException {
    public NoSendMessageException(String message) {
        super(message);
    }
    public NoSendMessageException(String message, Throwable cause) {super(message, cause);}

    public NoSendMessageException(Throwable cause) {super(cause);}
}
