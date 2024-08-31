package com.logitrack.orderservice.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * {@code NotificationServiceDto} представляет собой Data Transfer Object (DTO) для передачи информации о заказе в сервис уведомлений.
 *
 * <p>Этот класс используется для упаковки данных о заказе, таких как название продукта и номер заказа, для передачи между
 * различными слоями приложения или сервисами. DTO обеспечивает простой и удобный способ представления данных для отправки
 * уведомлений.</p>
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
public class NotificationServiceDto {
    String product_name;
    String order_number;
}
