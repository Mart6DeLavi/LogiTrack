package com.logitrack.inventoryservice.configs.kafka.configurations;

import com.logitrack.inventoryservice.deserializers.InventoryServiceDtoSerializer;
import com.logitrack.inventoryservice.dtos.InventoryServiceDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация для Kafka consumer.
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    /**
     * Создаёт фабрику consumer для Kafka.
     *
     * @return ConsumerFactory для InventoryServiceDto
     */
    @Bean
    public ConsumerFactory<String, InventoryServiceDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, InventoryServiceDtoSerializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Настраивает фабрику контейнеров KafkaListener.
     *
     * @return ConcurrentKafkaListenerContainerFactory для InventoryServiceDto
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryServiceDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InventoryServiceDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
