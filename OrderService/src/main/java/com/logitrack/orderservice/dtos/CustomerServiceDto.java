package com.logitrack.orderservice.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceDto {
    String product_name;
    LocalDateTime order_time;
    LocalDateTime estimated_delivery_time;
    String delivery_address;
    String order_number;
    Double price;
}
