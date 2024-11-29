package com.logitrack.orderservice.configs.kafka.configs.producer;

import com.logitrack.orderservice.dtos.producer.CustomerServiceDtoProducer;
import com.logitrack.orderservice.serializers.UniversalDtoSerializer;
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
public class CustomerServiceKafkaProducerConfig {

    @Bean
    public ProducerFactory<String, CustomerServiceDtoProducer> customerServiceProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UniversalDtoSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, CustomerServiceDtoProducer> customerServiceKafkaTemplate() {
        return new KafkaTemplate<>(customerServiceProducerFactory());
    }
}
