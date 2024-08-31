package com.logitrack.orderservice.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * {@code CustomerServiceDto} представляет собой Data Transfer Object (DTO) для передачи информации о заказе в сервис клиента.
 *
 * <p>Этот класс используется для упаковки данных заказа, таких как название продукта, время заказа, ожидаемое время доставки,
 * адрес доставки, номер заказа и цена. DTO обеспечивает простое и удобное представление данных для передачи между
 * различными слоями приложения или сервисами.</p>
 *
 * <p>Класс использует аннотации {@link Getter}, {@link Setter}, {@link RequiredArgsConstructor}, и {@link FieldDefaults}
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
public class CustomerServiceDto {
    String product_name;
    LocalDateTime order_time;
    LocalDateTime estimated_delivery_time;
    String delivery_address;
    String order_number;
    Double price;
}
