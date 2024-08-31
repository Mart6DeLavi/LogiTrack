package com.logitrack.orderservice.services;

import com.logitrack.orderservice.configs.kafka.producer.CustomerServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.InventoryServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.NotificationServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.PaymentsServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.data.repositories.OrderEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CreateOrderTest {

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
    void createOrderTest() {
        // given
        OrderEntity orderEntity = new OrderEntity();

        when(orderEntityRepository.save(orderEntity)).thenReturn(orderEntity);

        // when
        OrderEntity result = orderService.createOrder(orderEntity);

        // then
        assertNotNull(result);
        verify(orderEntityRepository, times(1)).save(orderEntity);
    }
}
