package com.logitrack.shippingservice.dtos.consumer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderServiceKafkaConsumerDto {
    private String orderNumber;
    private Integer productId;
    private String productName;
    private Double productPrice;
}
