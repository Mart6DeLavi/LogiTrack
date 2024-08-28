package com.logitrack.orderservice.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserOrderInformationDto {
    String product_name;
    String order_name;
    String client_address;
    Double price;
}
