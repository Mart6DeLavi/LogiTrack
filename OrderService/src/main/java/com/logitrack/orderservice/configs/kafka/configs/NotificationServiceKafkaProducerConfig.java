package com.logitrack.orderservice.configs.kafka.configs;

import com.logitrack.orderservice.dtos.NotificationServiceDto;
import com.logitrack.orderservice.serializers.NotificationServiceDtoSerializer;
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
public class NotificationServiceKafkaProducerConfig {

    @Bean
    public ProducerFactory<String, NotificationServiceDto> notificationServiceProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, NotificationServiceDtoSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, NotificationServiceDto> notificationServiceKafkaTemplate() {
        return new KafkaTemplate<>(notificationServiceProducerFactory());
    }
}
