package com.logitrack.orderservice.services;

import com.logitrack.orderservice.configs.kafka.consumer.InventoryServiceKafkaConsumer;
import com.logitrack.orderservice.configs.kafka.producer.CustomerServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.InventoryServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.NotificationServiceKafkaProducer;
import com.logitrack.orderservice.configs.kafka.producer.PaymentsServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.data.repositories.OrderEntityRepository;
import com.logitrack.orderservice.dtos.UserOrderInformationDto;
import com.logitrack.orderservice.dtos.consumer.InventoryServiceDtoConsumer;
import com.logitrack.orderservice.exceptions.NoSuchOrderException;
import com.logitrack.orderservice.threads.kafka.CustomerServiceKafkaProducerThread;
import com.logitrack.orderservice.threads.kafka.InventoryServiceKafkaProducerThread;
import com.logitrack.orderservice.threads.kafka.NotificationServiceKafkaProducerThread;
import com.logitrack.orderservice.threads.kafka.PaymentsServiceKafkaProducerThread;
import lombok.RequiredArgsConstructor;
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

    private final InventoryServiceKafkaConsumer inventoryServiceKafkaConsumer;

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
    public UserOrderInformationDto createOrder(OrderEntity orderEntity) {
        orderEntityRepository.save(orderEntity);

        sendToOtherServices(orderEntity);

        InventoryServiceDtoConsumer inventoryServiceDtoConsumer = inventoryServiceKafkaConsumer.getInventoryFuture().join();

        UserOrderInformationDto userOrderInformationDto = new UserOrderInformationDto();

        userOrderInformationDto.setOrder_number(orderEntity.getOrderNumber());
        userOrderInformationDto.setClient_address(orderEntity.getClientAddress());
        userOrderInformationDto.setManufacture(inventoryServiceDtoConsumer.getManufacture());
        userOrderInformationDto.setProduct_name(inventoryServiceDtoConsumer.getTitle());
        userOrderInformationDto.setPrice((double) inventoryServiceDtoConsumer.getPrice());
        return userOrderInformationDto;
    }

    private void sendToOtherServices(OrderEntity orderEntity) {
        CustomerServiceKafkaProducerThread customerServiceKafkaProducerThread =
                new CustomerServiceKafkaProducerThread(customerServiceKafkaProducer);
        customerServiceKafkaProducerThread.sendToCustomerService(orderEntity);

        InventoryServiceKafkaProducerThread inventoryServiceKafkaProducerThread =
                new InventoryServiceKafkaProducerThread(inventoryServiceKafkaProducer);
        inventoryServiceKafkaProducerThread.sendToInventoryService(orderEntity);

        NotificationServiceKafkaProducerThread notificationServiceKafkaProducerThread =
                new NotificationServiceKafkaProducerThread(notificationServiceKafkaProducer);
        notificationServiceKafkaProducerThread.sendToNotificationService(orderEntity);

        PaymentsServiceKafkaProducerThread paymentsServiceKafkaProducerThread =
                new PaymentsServiceKafkaProducerThread(paymentsServiceKafkaProducer);
        paymentsServiceKafkaProducerThread.sendToPaymentsService(orderEntity);
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
