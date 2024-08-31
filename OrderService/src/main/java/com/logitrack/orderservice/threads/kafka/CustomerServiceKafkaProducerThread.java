package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.CustomerServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.CustomerServiceDto;
import com.logitrack.orderservice.exceptions.kafka.CustomerServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
public class CustomerServiceKafkaProducerThread extends Thread {

    private final CustomerServiceKafkaProducer producer;
    private final OrderEntity orderEntity;

    /**
     * Запускает поток, который отправляет сообщение о заказе в сервис клиентов через Kafka.
     *
     * <p>Этот метод переопределяет {@link Thread#run()} и вызывает {@link #sendMessage()} для отправки сообщения.
     * В случае возникновения исключения при отправке сообщения, оно логируется и пробрасывается как
     * {@link CustomerServiceKafkaNotSentException}.</p>
     */
    @Override
    public void run() {
        log.info("Customer Service Thread Started");

        try {
            sendMessage();
        } catch (RuntimeException ex) {
            log.error("Error sending message to customer service: {}", ex.getMessage());
            throw new CustomerServiceKafkaNotSentException(ex.getMessage(), ex);
        }
    }

    /**
     * Создает объект {@link CustomerServiceDto} из данных заказа и отправляет его в сервис клиентов через Kafka.
     *
     * <p>Этот метод вызывает {@link CustomerServiceKafkaProducer#sendToCustomerService(String, Object)}
     * для отправки сообщения. Если возникает исключение при отправке сообщения, оно пробрасывается как
     * {@link CustomerServiceKafkaNotSentException}.</p>
     */
    private void sendMessage() {
        CustomerServiceDto customerServiceDto = new CustomerServiceDto();

        customerServiceDto.setProduct_name(orderEntity.getProductName());
        customerServiceDto.setOrder_time(orderEntity.getOrderTime());
        customerServiceDto.setEstimated_delivery_time(orderEntity.getEstimatedDeliveryTime());
        customerServiceDto.setOrder_time(orderEntity.getOrderTime());
        customerServiceDto.setDelivery_address(orderEntity.getClientAddress());
        customerServiceDto.setPrice(orderEntity.getPrice());

        try {
            producer.sendToCustomerService("order-service-to-customer-service", customerServiceDto);
        } catch (RuntimeException ex) {
            throw new CustomerServiceKafkaNotSentException(ex.getMessage(), ex);
        }
    }
}
