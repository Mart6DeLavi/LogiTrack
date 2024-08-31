package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.PaymentsServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.PaymentsServiceDto;
import com.logitrack.orderservice.exceptions.kafka.PaymentsServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PaymentsServiceKafkaProducerThread extends Thread{
    private final PaymentsServiceKafkaProducer producer;
    private final OrderEntity orderEntity;

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
