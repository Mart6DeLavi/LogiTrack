package com.logitrack.orderservice.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceDto {
    String product_name;
    LocalDateTime order_time;
    LocalDateTime estimated_delivery_time;
    String delivery_address;
    String order_number;
    Double price;
}
