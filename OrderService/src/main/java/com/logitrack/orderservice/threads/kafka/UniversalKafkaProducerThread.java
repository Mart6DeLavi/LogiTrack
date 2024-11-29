package com.logitrack.orderservice.threads.kafka;

import com.logitrack.orderservice.configs.kafka.producer.KafkaProducer;
import com.logitrack.orderservice.data.entities.OrderEntity;
import com.logitrack.orderservice.mapps.DtoMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@EnableAsync
public class UniversalKafkaProducerThread<T> {

    private ExecutorService executor;

    @PostConstruct
    public void init() {
        executor = new ThreadPoolExecutor(0, 10, 60L,
                TimeUnit.SECONDS, new SynchronousQueue<>(), r -> {
           Thread thread = new Thread(r);
           thread.setName("UniversalKafkaProducerThread-" + thread.getId());
           thread.setDaemon(true);
           return thread;
        });
    }

    @Async
    public void sendToKafka(OrderEntity orderEntity, String topic,
                            KafkaProducer<T> producer, DtoMapper<T> mapper) {
        executor.submit(() -> {
           try {
               T dto = mapper.map(orderEntity);
               log.info("Sending DTO to kafka topic {}: {}", topic, dto);
               producer.sendToKafka(topic, dto);
           } catch (RuntimeException ex) {
               log.error("Failed to send DTO to kafka topic {}: {}", topic, ex.getMessage());
               throw new RuntimeException("Failed to send DTO to kafka topic " + topic, ex);
           }
        });
    }

    @PreDestroy
    public void shutdownExecutor() {
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException ex) {
                executor.shutdownNow();
            }
        }
    }
}
