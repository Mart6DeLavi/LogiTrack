package com.logitrack.orderservice.services;

import com.logitrack.orderservice.configs.kafka.producer.CustomerServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.InventoryServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.NotificationServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.PaymentsServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.data.repositories.OrderEntityRepository;
import com.logitrack.orderservice.dtos.UserOrderInformationDto;
import com.logitrack.orderservice.exceptions.NoSuchOrderException;
import com.logitrack.orderservice.threads.kafka.CustomerServiceKafkaProducerThread;
import com.logitrack.orderservice.threads.kafka.InventoryServiceKafkaProducerThread;
import com.logitrack.orderservice.threads.kafka.NotificationServiceKafkaProducerThread;
import com.logitrack.orderservice.threads.kafka.PaymentsServiceKafkaProducerThread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderEntityRepository orderEntityRepository;

    private final CustomerServiceKafkaProducer customerServiceKafkaProducer;
    private final InventoryServiceKafkaProducer inventoryServiceKafkaProducer;
    private final PaymentsServiceKafkaProducer paymentsServiceKafkaProducer;
    private final NotificationServiceKafkaProducer notificationServiceKafkaProducer;

    public List<OrderEntity> getAllOrders() {
        return orderEntityRepository.findAll();
    }

    public OrderEntity createOrder(OrderEntity orderEntity) {
        new CustomerServiceKafkaProducerThread(customerServiceKafkaProducer, orderEntity).start();
        new InventoryServiceKafkaProducerThread(inventoryServiceKafkaProducer, orderEntity).start();
        new NotificationServiceKafkaProducerThread(notificationServiceKafkaProducer, orderEntity).start();
        new PaymentsServiceKafkaProducerThread(paymentsServiceKafkaProducer, orderEntity).start();
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
