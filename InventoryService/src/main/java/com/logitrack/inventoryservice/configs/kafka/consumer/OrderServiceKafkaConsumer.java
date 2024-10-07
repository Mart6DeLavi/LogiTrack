package com.logitrack.inventoryservice.configs.kafka.consumer;

import com.logitrack.inventoryservice.configs.kafka.producer.OrderServiceKafkaProducer;
import com.logitrack.inventoryservice.service.ProductService;
import com.logitrack.inventoryservice.dtos.InventoryServiceDto;
import com.logitrack.inventoryservice.entities.Product;
import com.logitrack.inventoryservice.threads.kafka.OrderServiceKafkaProducerThread;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceKafkaConsumer {

    private final ProductService productService;

    private final OrderServiceKafkaProducer orderServiceKafkaProducer;


    @KafkaListener(topics = "order-service-to-inventory-service", groupId = "order-service")
    public void consume(InventoryServiceDto inventoryServiceDto) {
        log.info("Consumed inventory service dto: {}", inventoryServiceDto.getProduct_id());

        Optional<Product> productOpt = Optional.ofNullable(productService.findProductById(inventoryServiceDto.getProduct_id()));

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            log.info("Product found: {}", product.getId());

            OrderServiceKafkaProducerThread producerThread =
                    new OrderServiceKafkaProducerThread(orderServiceKafkaProducer);

            producerThread.sendToOrderService(product);
            log.info("Message sent to order-service");
        } else {
            log.error("Product not found: {}", inventoryServiceDto.getProduct_id());
        }
    }
}
