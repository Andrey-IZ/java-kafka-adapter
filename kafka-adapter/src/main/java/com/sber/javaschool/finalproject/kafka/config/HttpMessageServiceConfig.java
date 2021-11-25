package com.sber.javaschool.finalproject.kafka.config;

import com.sber.javaschool.finalproject.json2http.service.JsonToHttpRequestServiceImpl;
import com.sber.javaschool.finalproject.json2http.service.api.HttpClient;
import com.sber.javaschool.finalproject.json2http.service.api.HttpRequest;
import com.sber.javaschool.finalproject.json2http.service.api.JsonToHttpRequestService;
import com.sber.javaschool.finalproject.json2http.service.http.request.HttpRequestBuilder;
import com.sber.javaschool.finalproject.json2http.utils.HttpArgsQueryFormatter;
import com.sber.javaschool.finalproject.json2http.utils.httpclients.HttpClientBasedOnRestAssured;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpMessageServiceConfig {
    @Bean
    public HttpClient httpClient() {
        return new HttpClientBasedOnRestAssured(requestService());
    }

    @Bean
    public JsonToHttpRequestService requestService() {
        return new JsonToHttpRequestServiceImpl(httpRequestBuilder());
    }

    @Bean
    public HttpRequest.Builder httpRequestBuilder() {
        var httpRequestBuilder = new HttpRequestBuilder();
        httpRequestBuilder.addArgsFormatter(new HttpArgsQueryFormatter());
        return httpRequestBuilder;
    }
}
