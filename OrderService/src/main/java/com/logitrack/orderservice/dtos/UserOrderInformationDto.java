package com.logitrack.orderservice.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * {@code UserOrderInformationDto} представляет собой Data Transfer Object (DTO) для передачи информации о заказе пользователя.
 *
 * <p>Этот класс используется для упаковки данных о заказе пользователя, таких как название продукта, имя заказа, адрес клиента и цена,
 * для передачи между различными слоями приложения или сервисами. DTO обеспечивает удобное представление данных для отображения
 * информации о заказе пользователю.</p>
 *
 * <p>Класс использует аннотации {@link Getter}, {@link AllArgsConstructor} и {@link FieldDefaults} для автоматической генерации
 * геттеров, создания конструктора со всеми аргументами и установки уровня доступа полей соответственно.</p>
 *
 * @see lombok.Getter
 * @see lombok.AllArgsConstructor
 * @see lombok.experimental.FieldDefaults
 */
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserOrderInformationDto {
    String product_name;
    String order_number;
    String client_address;
    String manufacture;
    Double price;
}
