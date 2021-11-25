package com.sber.javaschool.finalproject.kafka.service.consumer.impl;

import com.sber.javaschool.finalproject.json2http.service.api.HttpClient;
import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;
import com.sber.javaschool.finalproject.json2http.service.exceptions.HttpRequestException;
import com.sber.javaschool.finalproject.kafka.service.consumer.MessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerMessageService implements MessageConsumer {
    private final HttpClient httpClient;

    @Autowired
    public KafkaConsumerMessageService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @KafkaListener(id = "JsonHttpMessage",
            topics = {"${message.topic.name}"},
            groupId = "${message.topic.group.id}",
            containerFactory = "messageKafkaListenerContainerFactory")
    public void receive(MessageDto messageDto) {
        log.info(String.format("#### -> Consumed message -> %s", messageDto.toString()));
        try {
            httpClient.doRequest(messageDto);
        } catch (HttpRequestException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
