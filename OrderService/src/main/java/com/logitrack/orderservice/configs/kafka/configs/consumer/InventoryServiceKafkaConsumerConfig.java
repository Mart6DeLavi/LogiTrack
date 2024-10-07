package com.logitrack.orderservice.configs.kafka.configs.consumer;

import com.logitrack.orderservice.deserializers.InventoryServiceDtoDeserializer;
import com.logitrack.orderservice.dtos.consumer.InventoryServiceDtoConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class InventoryServiceKafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, InventoryServiceDtoConsumer> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, InventoryServiceDtoDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryServiceDtoConsumer> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InventoryServiceDtoConsumer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
