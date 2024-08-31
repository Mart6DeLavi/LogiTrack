package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.CustomerServiceKafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.dtos.CustomerServiceDto;
import com.logitrack.orderservice.exceptions.kafka.CustomerServiceKafkaNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomerServiceKafkaProducerThread extends Thread{

    private final CustomerServiceKafkaProducer producer;
    private final OrderEntity orderEntity;

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
        } catch (RuntimeException ex){
            throw new CustomerServiceKafkaNotSentException(ex.getMessage(), ex);
        }
    }
}
