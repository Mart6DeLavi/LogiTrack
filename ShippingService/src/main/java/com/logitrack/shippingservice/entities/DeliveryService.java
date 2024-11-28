package com.logitrack.shippingservice.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DeliveryService {
    private int serviceId;
    private String serviceName;
    private double serviceReputation;
}
