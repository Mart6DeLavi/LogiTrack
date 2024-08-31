package com.logitrack.orderservice.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * {@code InventoryServiceDto} представляет собой Data Transfer Object (DTO) для передачи информации о продукте в сервис инвентаря.
 *
 * <p>Этот класс используется для упаковки данных о продукте, таких как идентификатор продукта, для передачи между различными
 * слоями приложения или сервисами. DTO обеспечивает удобный способ представления данных для отправки или получения информации.</p>
 *
 * <p>Класс использует аннотации {@link Getter}, {@link Setter}, {@link RequiredArgsConstructor} и {@link FieldDefaults}
 * для автоматической генерации геттеров и сеттеров, создания конструктора с обязательными аргументами и установки
 * уровня доступа полей соответственно.</p>
 *
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.RequiredArgsConstructor
 * @see lombok.experimental.FieldDefaults
 */
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryServiceDto {
    int product_id;
}
