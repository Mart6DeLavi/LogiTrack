package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.PaymentsServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.PaymentsServiceDto;
import com.logitrack.orderservice.exceptions.kafka.PaymentsServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Поток {@code PaymentsServiceKafkaProducerThread} предназначен для отправки сообщений о заказах в сервис платежей
 * через Kafka. Этот поток работает асинхронно, чтобы не блокировать основной поток выполнения.
 *
 * <p>Поток использует {@link PaymentsServiceKafkaProducer} для отправки сообщений и обрабатывает исключения,
 * связанные с отправкой, логируя их и выбрасывая пользовательское исключение {@link PaymentsServiceKafkaNotSentException}.</p>
 */
@Slf4j
@RequiredArgsConstructor
public class PaymentsServiceKafkaProducerThread extends Thread{
    private final PaymentsServiceKafkaProducer producer;
    private final OrderEntity orderEntity;

    /**
     * Запускает поток, который отправляет сообщение о заказе в сервис платежей через Kafka.
     *
     * <p>Этот метод переопределяет {@link Thread#run()} и вызывает {@link #sendMessage()} для отправки сообщения.
     * В случае возникновения исключения при отправке сообщения, оно логируется и пробрасывается как
     * {@link PaymentsServiceKafkaNotSentException}.</p>
     */
    @Override
    public void run() {
        log.info("Payments Service Kafka Producer Thread started");

        try {
            sendMessage();
        } catch (RuntimeException ex) {
            log.error("Payments Service Kafka Producer Thread failed", ex);
            throw new PaymentsServiceKafkaNotSentException(ex.getMessage(), ex);
        }
    }

    /**
     * Создает объект {@link PaymentsServiceDto} из данных заказа и отправляет его в сервис платежей через Kafka.
     *
     * <p>Этот метод вызывает {@link PaymentsServiceKafkaProducer#sentToPaymentsService(String, Object)}
     * для отправки сообщения. Если возникает исключение при отправке сообщения, оно пробрасывается как
     * {@link PaymentsServiceKafkaNotSentException}.</p>
     */
    private void sendMessage() {
        PaymentsServiceDto paymentsServiceDto = new PaymentsServiceDto();

        paymentsServiceDto.setProduct_id(orderEntity.getProductId());
        paymentsServiceDto.setPrice(orderEntity.getPrice());

        try {
            producer.sentToPaymentsService("order-service-to-payments-service", paymentsServiceDto);
        } catch (RuntimeException ex) {
            throw new PaymentsServiceKafkaNotSentException(ex.getMessage());
        }
    }
}
