package com.logitrack.shippingservice.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class MappedProductListIntoJsonException extends JsonProcessingException {

    public MappedProductListIntoJsonException(JsonProcessingException exception) {
        super("Cannot mapped products list into JSON" + exception.getMessage());
    }
}
