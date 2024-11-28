package com.logitrack.shippingservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderProductInformationDto {
    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productQuantity")
    private Integer productQuantity;

    @JsonProperty("productPrice")
    private Double productPrice;
}
