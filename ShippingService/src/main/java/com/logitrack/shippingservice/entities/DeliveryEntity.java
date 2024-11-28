package com.logitrack.shippingservice.entities;

import com.logitrack.shippingservice.dtos.OrderProductInformationDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DeliveryEntity {
    private Long deliveryId;
    private Long userId;
    private String deliveryNumber;
    private int deliveryServiceId;
    private String deliveryAddress;
    private double orderSum;
    private List<OrderProductInformationDto> orderProducts;
    private int orderId;
    private String orderDocumentMongoId;
}
