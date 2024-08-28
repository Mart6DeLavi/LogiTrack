package com.logitrack.orderservice.controllers;

import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.UserOrderInformationDto;
import com.logitrack.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/all")
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/creating")
    public OrderEntity createOrder(@RequestBody OrderEntity order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/finding/{orderNumber}")
    public UserOrderInformationDto findOrders(@PathVariable String orderNumber) {
        return orderService.findOrderByOrderNumber(orderNumber);
    }

    @DeleteMapping("/delete/{orderNumber}")
    public String deleteOrder(@PathVariable String orderNumber) {
        return orderService.deleteOrder(orderNumber);
    }
}