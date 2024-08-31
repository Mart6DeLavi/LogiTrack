package com.logitrack.orderservice.services;

import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.data.repositories.OrderEntityRepository;
import com.logitrack.orderservice.dtos.UserOrderInformationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MapToUserOrderInformationDtoTest {
    @Mock
    private OrderEntityRepository orderEntityRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mapToUserOrderInformationDtoTest() {
        // given
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProductName("Product 1");
        orderEntity.setOrderNumber("12345");
        orderEntity.setClientAddress("123 Street");
        orderEntity.setPrice(100.0);

        // when
        UserOrderInformationDto dto = orderService.mapToUserOrderInformationDto(orderEntity);

        // then
        assertNotNull(dto);
        assertEquals("Product 1", orderEntity.getProductName());
        assertEquals("12345", orderEntity.getOrderNumber());
        assertEquals("123 Street", orderEntity.getClientAddress());
        assertEquals(100.0, dto.getPrice());
    }
}
