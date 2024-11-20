package com.logitrack.orderservice.configs.kafka.configs.producer;

import com.logitrack.orderservice.dtos.producer.InventoryServiceDtoProducer;
import com.logitrack.orderservice.serializers.InventoryServiceDtoSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class InventoryServiceKafkaProducerConfig {

    @Bean
    public ProducerFactory<String, InventoryServiceDtoProducer> inventoryServiceProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, InventoryServiceDtoSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, InventoryServiceDtoProducer> inventoryServiceKafkaTemplate() {
        return new KafkaTemplate<>(inventoryServiceProducerFactory());
    }
}
