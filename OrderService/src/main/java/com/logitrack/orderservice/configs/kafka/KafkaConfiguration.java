package com.logitrack.orderservice.configs.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

/**
 * {@code KafkaConfiguration} содержит настройки и конфигурацию для работы с Kafka в приложении Spring Boot.
 *
 * <p>Этот класс настраивает компоненты, необходимые для взаимодействия с Kafka, такие как фабрики продюсеров и потребителей,
 * а также шаблоны и фабрики для обработки сообщений. Конфигурация включает настройку сериализации и десериализации для ключей и значений.</p>
 *
 * <p>Класс помечен аннотацией {@link Configuration}, что указывает Spring, что этот класс содержит бины конфигурации
 * для контекста приложения.</p>
 *
 * @see org.springframework.kafka.core.KafkaTemplate
 * @see org.springframework.kafka.core.ConsumerFactory
 * @see org.springframework.kafka.core.ProducerFactory
 * @see org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
 * @see org.springframework.boot.autoconfigure.kafka.KafkaProperties
 */
@Configuration
public class KafkaConfiguration {

    /**
     * Создает {@link DefaultKafkaProducerFactory} для продюсеров Kafka с сериализацией значений и ключей как строки.
     *
     * <p>Этот метод настраивает {@link DefaultKafkaProducerFactory} для отправки сообщений в Kafka. Сериализация значений
     * и ключей производится с помощью {@link StringSerializer}. Свойства продюсера загружаются из {@link KafkaProperties}.</p>
     *
     * @param properties Свойства Kafka, используемые для настройки продюсера.
     * @return Конфигурированная фабрика продюсеров.
     */
    @Bean
    DefaultKafkaProducerFactory<String, String> stringProducerFactory(KafkaProperties properties) {
        Map<String, Object> producerProperties = properties.buildProducerProperties(null);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    /**
     * Создает {@link KafkaTemplate} для отправки сообщений в Kafka.
     *
     * <p>Этот метод настраивает {@link KafkaTemplate} с использованием конфигурированной фабрики продюсеров, чтобы
     * можно было отправлять сообщения в Kafka топики.</p>
     *
     * @param stringProducerFactory Конфигурированная фабрика продюсеров для Kafka.
     * @return Конфигурированный шаблон Kafka для отправки сообщений.
     */
    @Bean
    KafkaTemplate<String, String> stringKafkaTemplate(DefaultKafkaProducerFactory<String, String> stringProducerFactory) {
        return new KafkaTemplate<>(stringProducerFactory);
    }

    /**
     * Создает {@link ConsumerFactory} для потребителей Kafka с десериализацией значений и ключей как строки.
     *
     * <p>Этот метод настраивает {@link DefaultKafkaConsumerFactory} для получения сообщений из Kafka. Десериализация значений
     * и ключей производится с помощью {@link StringDeserializer}. Свойства потребителя загружаются из {@link KafkaProperties}.</p>
     *
     * @param kafkaProperties Свойства Kafka, используемые для настройки потребителя.
     * @return Конфигурированная фабрика потребителей.
     */
    @Bean
    public ConsumerFactory<String, String> stringConsumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties(null);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Создает {@link KafkaListenerContainerFactory} для обработки сообщений из Kafka.
     *
     * <p>Этот метод настраивает {@link ConcurrentKafkaListenerContainerFactory} для создания контейнеров прослушивателей сообщений.
     * Контейнеры настроены на обработку сообщений по одному за раз, не в пакетном режиме.</p>
     *
     * @param stringConsumerFactory Конфигурированная фабрика потребителей для Kafka.
     * @return Конфигурированная фабрика контейнеров прослушивателей Kafka.
     */
    @Bean
    public KafkaListenerContainerFactory<?> stringListenerFactory(ConsumerFactory<String, String> stringConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory);
        factory.setBatchListener(false);
        return factory;
    }
}