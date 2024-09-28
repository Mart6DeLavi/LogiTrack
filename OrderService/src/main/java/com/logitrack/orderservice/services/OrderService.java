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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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

    private final CustomerServiceKafkaProducer customerServiceKafkaProducer;
    private final InventoryServiceKafkaProducer inventoryServiceKafkaProducer;
    private final PaymentsServiceKafkaProducer paymentsServiceKafkaProducer;
    private final NotificationServiceKafkaProducer notificationServiceKafkaProducer;

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
     * @param orderEntity Информация о заказе, который нужно создать.
     * @return Сохраненный объект заказа.
     */
    public OrderEntity createOrder(OrderEntity orderEntity) {
//        new CustomerServiceKafkaProducerThread(customerServiceKafkaProducer, orderEntity).start();
//        new InventoryServiceKafkaProducerThread(inventoryServiceKafkaProducer, orderEntity).start();
//        new NotificationServiceKafkaProducerThread(notificationServiceKafkaProducer, orderEntity).start();
//        new PaymentsServiceKafkaProducerThread(paymentsServiceKafkaProducer, orderEntity).start();
        Thread inventoryServiceKafkaProducerThread = new InventoryServiceKafkaProducerThread(inventoryServiceKafkaProducer, orderEntity);
        inventoryServiceKafkaProducerThread.start();
        return orderEntityRepository.save(orderEntity);
    }

    /**
     * Находит заказ по его номеру.
     *
     * @param orderNumber Номер заказа, который нужно найти.
     * @return Информация о заказе в виде объекта {@link UserOrderInformationDto}.
     * @throws NoSuchOrderException Если заказ с указанным номером не найден.
     */
    public UserOrderInformationDto findOrderByOrderNumber(String orderNumber) {
        OrderEntity orderEntity = orderEntityRepository
                .findOrderEntityByOrderNumber(orderNumber)
                .orElseThrow(() -> new NoSuchOrderException(orderNumber));
        return mapToUserOrderInformationDto(orderEntity);
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

    /**
     * Преобразует объект {@link OrderEntity} в {@link UserOrderInformationDto}.
     *
     * @param orderEntity Объект заказа, который нужно преобразовать.
     * @return Преобразованный объект {@link UserOrderInformationDto}.
     */
    public UserOrderInformationDto mapToUserOrderInformationDto(OrderEntity orderEntity) {
        return new UserOrderInformationDto(
                orderEntity.getProductName(),
                orderEntity.getOrderNumber(),
                orderEntity.getClientAddress(),
                orderEntity.getPrice()
        );
    }
}
