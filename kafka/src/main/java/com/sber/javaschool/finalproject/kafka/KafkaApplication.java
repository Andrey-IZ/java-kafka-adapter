package com.sber.javaschool.finalproject.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.sber.javaschool.finalproject.kafka.config",
        "com.sber.javaschool.finalproject.kafka.controller",
        "com.sber.javaschool.finalproject.kafka.service",
})
@PropertySource({"classpath:kafka.properties"})
public class KafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }
}


