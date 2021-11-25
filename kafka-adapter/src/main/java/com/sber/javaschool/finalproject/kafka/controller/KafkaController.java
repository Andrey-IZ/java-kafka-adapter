package com.sber.javaschool.finalproject.kafka.controller;

import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;
import com.sber.javaschool.finalproject.kafka.service.producer.ProducerMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final ProducerMessageService producerMessageService;

    @RequestMapping(value = "/publish", method = RequestMethod.POST, consumes = "application/json")
    public void sendMessageToKafkaTopic(@RequestBody MessageDto messageDto) {
        this.producerMessageService.sendMessage(messageDto);
    }


}