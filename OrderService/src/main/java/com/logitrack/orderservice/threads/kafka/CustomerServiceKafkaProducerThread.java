package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.CustomerServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.CustomerServiceDto;
import com.logitrack.orderservice.exceptions.kafka.CustomerServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Поток {@code CustomerServiceKafkaProducerThread} предназначен для отправки сообщений о заказах в сервис
 * клиентов через Kafka. Этот поток создается для обработки сообщений асинхронно, чтобы не блокировать основной
 * поток выполнения.
 *
 * <p>Поток использует {@link CustomerServiceKafkaProducer} для отправки сообщений и обрабатывает исключения,
 * связанные с отправкой, логируя их и выбрасывая пользовательское исключение {@link CustomerServiceKafkaNotSentException}.</p>
 */
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class CustomerServiceKafkaProducerThread {

    private final CustomerServiceKafkaProducer producer;

    private static final String CUSTOMER_TOPIC = "order-service-to-customer-service";

    @Async
    public void sendToCustomerService(OrderEntity orderEntity) {
        Thread customerServiceKafkaProducerThread = new Thread(() -> {
            CustomerServiceDto customerServiceDto = new CustomerServiceDto();

            try {
                customerServiceDto.setProduct_name(orderEntity.getProductName());
                customerServiceDto.setOrder_time(orderEntity.getOrderTime());
                customerServiceDto.setEstimated_delivery_time(orderEntity.getEstimatedDeliveryTime());
                customerServiceDto.setDelivery_address(orderEntity.getClientAddress());
                customerServiceDto.setPrice(orderEntity.getPrice());

                producer.sendToCustomerService(CUSTOMER_TOPIC, customerServiceDto);
            } catch (RuntimeException ex) {
                throw new CustomerServiceKafkaNotSentException(ex.getMessage(), ex);
            }
        });

        customerServiceKafkaProducerThread.start();
    }
}
