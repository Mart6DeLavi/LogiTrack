package com.logitrack.inventoryservice.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryServiceDto {
    int product_id;
}
