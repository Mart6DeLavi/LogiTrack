package com.logitrack.orderservice.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * {@code PaymentsServiceDto} представляет собой Data Transfer Object (DTO) для передачи информации о платеже в сервис платежей.
 *
 * <p>Этот класс используется для упаковки данных о платеже, таких как идентификатор продукта и цена, для передачи между
 * различными слоями приложения или сервисами. DTO обеспечивает удобное представление данных для обработки и выполнения
 * операций, связанных с платежами.</p>
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
public class PaymentsServiceDto {
    int product_id;
    Double price;
}
