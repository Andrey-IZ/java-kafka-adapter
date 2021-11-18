package com.sber.javaschool.finalproject.kafka.config;

import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.producer.id}")
    private String kafkaProducerId;

    @Bean
    public ProducerFactory<String, MessageDto> messageProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);
        configProps.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "20971520");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, MessageDto> messageKafkaTemplate() {
        return new KafkaTemplate<>(messageProducerFactory());
    }


}
