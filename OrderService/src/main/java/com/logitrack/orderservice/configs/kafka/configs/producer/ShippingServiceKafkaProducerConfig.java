package com.logitrack.orderservice.configs.kafka.configs.producer;

import com.logitrack.orderservice.dtos.producer.ShippingServiceDtoProducer;
import com.logitrack.orderservice.serializers.ShippingServiceDtoSerializer;
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
public class ShippingServiceKafkaProducerConfig {

    @Bean
    public ProducerFactory<String, ShippingServiceDtoProducer> shippingServiceProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ShippingServiceDtoSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, ShippingServiceDtoProducer> shippingServiceKafkaTemplate() {
        return new KafkaTemplate<>(shippingServiceProducerFactory());
    }
}