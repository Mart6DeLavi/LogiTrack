package com.logitrack.orderservice.services;

import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.data.repositories.OrderEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetAllOrdersTest {
    @Mock
    private OrderEntityRepository orderEntityRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrdersTest() {
        OrderEntity order1 = new OrderEntity();
        OrderEntity order2 = new OrderEntity();

        List<OrderEntity> orders = Arrays.asList(order1, order2);

        when(orderEntityRepository.findAll()).thenReturn(orders);

        List<OrderEntity> result = orderService.getAllOrders();

        assertEquals(2, result.size());
        verify(orderEntityRepository, times(1)).findAll();
    }
}
