package com.logitrack.orderservice.services;

import com.logitrack.orderservice.configs.kafka.producer.CustomerServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.InventoryServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.NotificationServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.PaymentsServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.data.repositories.OrderEntityRepository;
import com.logitrack.orderservice.dtos.UserOrderInformationDto;
import com.logitrack.orderservice.exceptions.NoSuchOrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTests {
    @Mock
    private OrderEntityRepository orderEntityRepository;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private CustomerServiceKafkaProducer customerServiceKafkaProducer;

    @Mock
    private InventoryServiceKafkaProducer inventoryServiceKafkaProducer;

    @Mock
    private PaymentsServiceKafkaProducer paymentsServiceKafkaProducer;

    @Mock
    private NotificationServiceKafkaProducer notificationServiceKafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findOrderByOrderNumberTest() {
        // given
        String orderNumber = "12345";
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNumber(orderNumber);

        when(orderEntityRepository.findOrderEntityByOrderNumber(orderNumber))
                .thenReturn(Optional.of(orderEntity));

        // when
        UserOrderInformationDto dto = orderService.findOrderByOrderNumber(orderNumber);

        // then
        assertNotNull(dto);
        assertEquals(orderNumber, orderNumber);
        verify(orderEntityRepository, times(1)).findOrderEntityByOrderNumber(orderNumber);
    }

    @Test
    void findOrderByOrderNumberNotFoundTest() {
        // given
        String orderNumber = "12345";

        when(orderEntityRepository.findOrderEntityByOrderNumber(orderNumber))
                .thenReturn(Optional.empty());

        // when/then
        assertThrows(NoSuchOrderException.class, () -> {
            orderService.findOrderByOrderNumber(orderNumber);
        });
    }
}
