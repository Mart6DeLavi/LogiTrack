package com.logitrack.orderservice.dtos.producer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ShippingServiceDtoProducer {
    private String orderNumber;
    private Integer productId;
    private String productName;
    private Double productPrice;
}
