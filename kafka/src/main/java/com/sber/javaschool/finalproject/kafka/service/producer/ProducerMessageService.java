package com.sber.javaschool.finalproject.kafka.service.producer;

import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;

public interface ProducerMessageService {

    void sendMessage(String topic, MessageDto messageDto);

    void sendMessage(MessageDto messageDto);
}
