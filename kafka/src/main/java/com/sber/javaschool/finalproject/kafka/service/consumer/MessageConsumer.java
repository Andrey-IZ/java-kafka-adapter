package com.sber.javaschool.finalproject.kafka.service.consumer;

import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;

import java.io.IOException;

public interface MessageConsumer {

    void receive(MessageDto message) throws IOException;
}