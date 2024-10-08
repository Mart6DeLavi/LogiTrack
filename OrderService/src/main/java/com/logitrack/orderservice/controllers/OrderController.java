package com.logitrack.orderservice.controllers;

import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.UserOrderInformationDto;
import com.logitrack.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code OrderController} предоставляет REST API для управления заказами.
 *
 * <p>Этот контроллер обрабатывает HTTP-запросы для выполнения операций с заказами, таких как создание, получение, поиск и удаление заказов.
 * Он использует {@link OrderService} для выполнения бизнес-логики и обработки запросов.</p>
 *
 * <p>Класс помечен аннотацией {@link RestController}, что указывает Spring, что этот класс является контроллером REST и его методы
 * обрабатывают HTTP-запросы. Аннотация {@link RequestMapping} задает базовый путь для всех маршрутов этого контроллера.</p>
 *
 * @see OrderService
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    private final static String GET_ALL_ORDERS = "/all";
    private final static String CREATE_ORDER = "/creating";
    private final static String FIND_ORDER = "/finding/{orderNumber}";
    private final static String DELETE_ORDER = "/delete/{orderNumber}";

    /**
     * Обрабатывает GET-запрос для получения всех заказов.
     *
     * <p>Этот метод вызывает {@link OrderService#getAllOrders()} для получения списка всех заказов в системе.</p>
     *
     * @return Список всех заказов {@link OrderEntity}.
     */
    @GetMapping(GET_ALL_ORDERS)
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    /**
     * Обрабатывает POST-запрос для создания нового заказа.
     *
     * <p>Этот метод вызывает {@link OrderService#createOrder(OrderEntity)} для создания нового заказа на основе переданных данных.</p>
     *
     * @param orderEntity Данные нового заказа, которые должны быть созданы.
     * @return Созданный заказ {@link OrderEntity}.
     */
    @PostMapping(CREATE_ORDER)
    public UserOrderInformationDto createOrder(@RequestBody OrderEntity orderEntity) {
        return orderService.createOrder(orderEntity);
    }

//    /**
//     * Обрабатывает GET-запрос для поиска заказа по номеру заказа.
//     *
//     * <p>Этот метод вызывает {@link OrderService#findOrderByOrderNumber(String)} для поиска заказа по номеру и возвращает
//     * информацию о заказе.</p>
//     *
//     * @param orderNumber Номер заказа, который нужно найти.
//     * @return Информация о заказе {@link UserOrderInformationDto}.
//     */
//    @GetMapping(FIND_ORDER)
//    public UserOrderInformationDto findOrders(@PathVariable String orderNumber) {
//        return orderService.findOrderByOrderNumber(orderNumber);
//    }

    /**
     * Обрабатывает DELETE-запрос для удаления заказа по номеру заказа.
     *
     * <p>Этот метод вызывает {@link OrderService#deleteOrder(String)} для удаления заказа по номеру и возвращает
     * сообщение об успешном удалении.</p>
     *
     * @param orderNumber Номер заказа, который нужно удалить.
     * @return Сообщение об успешном удалении заказа.
     */
    @DeleteMapping(DELETE_ORDER)
    public String deleteOrder(@PathVariable String orderNumber) {
        return orderService.deleteOrder(orderNumber);
    }


}