package com.logitrack.orderservice.exceptions;

/**
 * Исключение {@code NoSuchOrderException} возникает, когда запрашиваемый заказ не найден.
 *
 * <p>Это исключение используется для обработки случаев, когда заказ с указанным номером не существует в системе.</p>
 *
 * <p>Класс расширяет {@link RuntimeException} и предоставляет конструктор для создания исключения с форматом сообщения,
 * включающим номер заказа, который не был найден.</p>
 *
 * @see RuntimeException
 */
public class NoSuchOrderException extends RuntimeException {
    /**
     * Создает новое исключение {@code NoSuchOrderException} с сообщением, указывающим на отсутствие заказа.
     *
     * <p>Сообщение включает номер заказа, который не был найден.</p>
     *
     * @param order_number Номер заказа, который не был найден.
     */
    public NoSuchOrderException(String order_number) {
        super(String.format("No such order: %s", order_number));
    }
}
