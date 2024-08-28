package com.logitrack.orderservice.services;

import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.data.repositories.OrderEntityRepository;
import com.logitrack.orderservice.dtos.UserOrderInformationDto;
import com.logitrack.orderservice.exceptions.NoSuchOrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderEntityRepository orderEntityRepository;

    public List<OrderEntity> getAllOrders() {
        return orderEntityRepository.findAll();
    }

    public OrderEntity createOrder(OrderEntity orderEntity) {
        return orderEntityRepository.save(orderEntity);
    }

    public UserOrderInformationDto findOrderByOrderNumber(String orderNumber) {
        OrderEntity orderEntity = orderEntityRepository
                .findOrderEntityByOrderNumber(orderNumber)
                .orElseThrow(() -> new NoSuchOrderException(orderNumber));
        return mapToUserOrderInformationDto(orderEntity);
    }

    public String deleteOrder(String orderNumber) {
        OrderEntity orderEntity = orderEntityRepository
                .findOrderEntityByOrderNumber(orderNumber)
                .orElseThrow(() -> new NoSuchOrderException(orderNumber));
        orderEntityRepository.delete(orderEntity);
        return String.format("Order: %s deleted successfully", orderNumber);
    }

    public UserOrderInformationDto mapToUserOrderInformationDto(OrderEntity orderEntity) {
        return new UserOrderInformationDto(
                orderEntity.getProductName(),
                orderEntity.getOrderNumber(),
                orderEntity.getClientAddress(),
                orderEntity.getPrice()
        );
    }
}
