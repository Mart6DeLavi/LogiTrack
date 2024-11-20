package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.PaymentsServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.producer.PaymentsServiceDtoProducer;
import com.logitrack.orderservice.exceptions.kafka.PaymentsServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * Поток {@code PaymentsServiceKafkaProducerThread} предназначен для отправки сообщений о заказах в сервис платежей
 * через Kafka. Этот поток работает асинхронно, чтобы не блокировать основной поток выполнения.
 *
 * <p>Поток использует {@link PaymentsServiceKafkaProducer} для отправки сообщений и обрабатывает исключения,
 * связанные с отправкой, логируя их и выбрасывая пользовательское исключение {@link PaymentsServiceKafkaNotSentException}.</p>
 */
@Slf4j
@RequiredArgsConstructor
public class PaymentsServiceKafkaProducerThread extends Thread {
    private final PaymentsServiceKafkaProducer producer;

    private static final String PAYMENTS_TOPIC = "order-service-to-payments-service";

    @Async
    public void sendToPaymentsService(OrderEntity orderEntity) {
        Thread paymentsServiceKafkaProducerThread = new Thread(() -> {
           PaymentsServiceDtoProducer paymentsServiceDtoProducer = new PaymentsServiceDtoProducer();

           paymentsServiceDtoProducer.setProduct_id(orderEntity.getProductId());
           paymentsServiceDtoProducer.setPrice(orderEntity.getPrice());

           try {
               producer.sentToPaymentsService(PAYMENTS_TOPIC, paymentsServiceDtoProducer);
           } catch (RuntimeException ex) {
               throw new PaymentsServiceKafkaNotSentException(ex.getMessage(), ex);
           }
        });

        paymentsServiceKafkaProducerThread.start();
    }
}
