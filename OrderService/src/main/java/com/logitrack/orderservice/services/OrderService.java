package com.logitrack.orderservice.services;

import com.logitrack.orderservice.configs.kafka.consumer.InventoryServiceKafkaConsumer;
import com.logitrack.orderservice.configs.kafka.producer.*;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.data.repositories.OrderEntityRepository;
import com.logitrack.orderservice.dtos.UserOrderCreationDto;
import com.logitrack.orderservice.dtos.UserOrderInformationDto;
import com.logitrack.orderservice.dtos.consumer.InventoryServiceDtoConsumer;
import com.logitrack.orderservice.dtos.producer.*;
import com.logitrack.orderservice.exceptions.NoSuchOrderException;
import com.logitrack.orderservice.mapps.*;
import com.logitrack.orderservice.threads.kafka.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис {@code OrderService} предназначен для управления заказами.
 *
 * <p>Этот сервис предоставляет методы для получения всех заказов, создания нового заказа, поиска заказа по номеру
 * и удаления заказа. При создании заказа, сообщения отправляются в различные сервисы через Kafka в отдельных потоках.</p>
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderEntityRepository orderEntityRepository;

    private final UniversalKafkaProducerThread<CustomerServiceDtoProducer> customerServiceDtoProducerUniversalKafkaProducerThread;
    private final UniversalKafkaProducerThread<InventoryServiceDtoProducer> inventoryServiceDtoProducerUniversalKafkaProducerThread;
    private final UniversalKafkaProducerThread<NotificationServiceDtoProducer> notificationServiceDtoProducerUniversalKafkaProducerThread;
    private final UniversalKafkaProducerThread<PaymentsServiceDtoProducer> paymentsServiceDtoProducerUniversalKafkaProducerThread;
    private final UniversalKafkaProducerThread<ShippingServiceDtoProducer> shippingServiceDtoProducerUniversalKafkaProducerThread;

    private final CustomerServiceKafkaProducer customerServiceKafkaProducer;
    private final InventoryServiceKafkaProducer inventoryServiceKafkaProducer;
    private final NotificationServiceKafkaProducer notificationServiceKafkaProducer;
    private final PaymentsServiceKafkaProducer paymentsServiceKafkaProducer;
    private final ShippingServiceKafkaProducer shippingServiceKafkaProducer;

    private final CustomerServiceDtoMapper customerServiceDtoMapper;
    private final InventoryServiceDtoMapper inventoryServiceDtoMapper;
    private final NotificationServiceDtoMapper notificationServiceDtoMapper;
    private final PaymentsServiceDtoMapper paymentsServiceDtoMapper;
    private final ShippingServiceDtoMapper shippingServiceDtoMapper;

    private final InventoryServiceKafkaConsumer inventoryServiceKafkaConsumer;


    private static final String CUSTOMER_TOPIC = "order-service-to-customer-service";
    private static final String INVENTORY_TOPIC = "order-service-to-inventory-service";
    private static final String NOTIFICATION_TOPIC = "order-service-to-notification-service";
    private static final String PAYMENTS_TOPIC = "order-service-to-payment-service";
    private static final String SHIPPING_TOPIC = "order-service-to-shipping-service";

    /**
     * Возвращает список всех заказов.
     *
     * @return Список всех заказов.
     */
    public List<OrderEntity> getAllOrders() {
        return orderEntityRepository.findAll();
    }

        /**
     * Создает новый заказ и отправляет сообщения о заказе в соответствующие сервисы через Kafka в отдельных потоках.
     *
     * <p>Для каждого сервиса создается отдельный поток, который обрабатывает отправку сообщения в Kafka.</p>
     *
     * @param userOrderCreationDto Информация о заказе, который нужно создать.
     * @return Сохраненный объект заказа.
     */


    public UserOrderInformationDto createOrder(UserOrderCreationDto userOrderCreationDto) {
        OrderEntity orderEntity = new OrderEntity();
        sendToOtherServices(userOrderCreationDto, orderEntity);
        InventoryServiceDtoConsumer inventoryServiceDtoConsumer = inventoryServiceKafkaConsumer.getInventoryFuture().join();
        UserOrderInformationDto userOrderInformationDto = new UserOrderInformationDto();

        orderEntity.setClientName("Blank Name");
        orderEntity.setClientSecondName("Blank SecondName");
        orderEntity.setClientPhone("Blank Phone");
        orderEntity.setClientEmail("Blank Email");
        orderEntity.setProductName(inventoryServiceDtoConsumer.getTitle());
        orderEntity.setProductId(userOrderCreationDto.getProductId());
        orderEntity.setOrderTime(LocalDateTime.now());
        orderEntity.setEstimatedDeliveryTime(LocalDateTime.now().plusDays(3));
        orderEntity.setClientAddress("Blank Client Address");
        orderEntity.setSendingAddress("Blank Sending Address");
        orderEntity.setOrderNumber("Blank Order Number");
        orderEntity.setPrice(inventoryServiceDtoConsumer.getPrice());

        userOrderInformationDto.setOrder_number("Blank Order Number");
        userOrderInformationDto.setClient_address(orderEntity.getClientAddress());
        userOrderInformationDto.setManufacture(inventoryServiceDtoConsumer.getManufacture());
        userOrderInformationDto.setProduct_name(inventoryServiceDtoConsumer.getTitle());
        userOrderInformationDto.setPrice((double) inventoryServiceDtoConsumer.getPrice());

        orderEntityRepository.save(orderEntity);
        return userOrderInformationDto;
    }

    private void sendToOtherServices(UserOrderCreationDto userOrderCreationDto, OrderEntity orderEntity) {
        customerServiceDtoProducerUniversalKafkaProducerThread.sendToKafka(
                orderEntity,
                CUSTOMER_TOPIC,
                customerServiceKafkaProducer,
                customerServiceDtoMapper::map
        );

        inventoryServiceDtoProducerUniversalKafkaProducerThread.sendToKafka(
                orderEntity,
                INVENTORY_TOPIC,
                inventoryServiceKafkaProducer,
                order -> inventoryServiceDtoMapper.map(userOrderCreationDto)
        );

        notificationServiceDtoProducerUniversalKafkaProducerThread.sendToKafka(
                orderEntity,
                NOTIFICATION_TOPIC,
                notificationServiceKafkaProducer,
                notificationServiceDtoMapper::map
        );

        paymentsServiceDtoProducerUniversalKafkaProducerThread.sendToKafka(
                orderEntity,
                PAYMENTS_TOPIC,
                paymentsServiceKafkaProducer,
                paymentsServiceDtoMapper::map
        );

        shippingServiceDtoProducerUniversalKafkaProducerThread.sendToKafka(
                orderEntity,
                SHIPPING_TOPIC,
                shippingServiceKafkaProducer,
                shippingServiceDtoMapper::map
        );
    }


    /**
     * Удаляет заказ по его номеру.
     *
     * @param orderNumber Номер заказа, который нужно удалить.
     * @return Сообщение об успешном удалении заказа.
     * @throws NoSuchOrderException Если заказ с указанным номером не найден.
     */
    public String deleteOrder(String orderNumber) {
        OrderEntity orderEntity = orderEntityRepository
                .findOrderEntityByOrderNumber(orderNumber)
                .orElseThrow(() -> new NoSuchOrderException(orderNumber));
        orderEntityRepository.delete(orderEntity);
        return String.format("Order: %s deleted successfully", orderNumber);
    }

}
