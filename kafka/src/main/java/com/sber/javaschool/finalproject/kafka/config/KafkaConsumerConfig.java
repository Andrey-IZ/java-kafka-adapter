package com.sber.javaschool.finalproject.kafka.config;

import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${message.topic.group.id}")
    private String messageGroupId;

    @Value("${kafka.consumer.id}")
    private String kafkaConsumerId;

    public ConsumerFactory<String, MessageDto> messageConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, messageGroupId);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaConsumerId);

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");  // deserialize all

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "20971520"); // 20 MB
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "20971520");   // 20 MB

        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(MessageDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageDto> messageKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(messageConsumerFactory());
        return factory;
    }
}
