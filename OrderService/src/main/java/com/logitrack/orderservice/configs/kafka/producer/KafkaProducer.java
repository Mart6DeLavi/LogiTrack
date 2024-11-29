package com.logitrack.orderservice.configs.kafka.producer;

@FunctionalInterface
public interface KafkaProducer<T> {
    void sendToKafka(String topic, T value);
}
