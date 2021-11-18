package com.sber.javaschool.finalproject.kafka.service.producer.impl;

import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;
import com.sber.javaschool.finalproject.kafka.service.producer.ProducerMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafKaProducerMessageService implements ProducerMessageService {
	@Value(value = "${message.topic.name}")
	private String topicName;

	private final KafkaTemplate<String, MessageDto> kafkaTemplate;

	@Override
	public void sendMessage(String topic, MessageDto messageDto) {
		this.kafkaTemplate.send(topic, messageDto)
				.addCallback(
						result -> log.info("Message sent to topic: {}", messageDto),
						ex -> log.error("Failed to send message", ex)
				);
	}

	@Override
	public void sendMessage(MessageDto messageDto) {
		sendMessage(topicName, messageDto);
	}
}
