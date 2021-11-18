package com.sber.javaschool.finalproject.json2http.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sber.javaschool.finalproject.json2http.service.api.HttpRequest;
import com.sber.javaschool.finalproject.json2http.service.api.JsonToHttpRequestService;
import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;
import com.sber.javaschool.finalproject.json2http.service.exceptions.JsonParseException;
import com.sber.javaschool.finalproject.json2http.service.http.HttpHeaders;
import com.sber.javaschool.finalproject.json2http.service.http.body.HttpBodyString;
import com.sber.javaschool.finalproject.json2http.service.http.version.HttpVersion;

import java.net.URI;
import java.util.Objects;

public class JsonToHttpRequestServiceImpl implements JsonToHttpRequestService {

    private final HttpRequest.Builder httpRequestBuilder;

    public JsonToHttpRequestServiceImpl(HttpRequest.Builder httpRequestBuilder) {
        this.httpRequestBuilder = httpRequestBuilder;
    }

    @Override
    public HttpRequest parse(String jsonString) throws JsonParseException {
        MessageDto jsonMessage = parseJsonMessage(jsonString);
        return parse(jsonMessage);
    }

    @Override
    public HttpRequest parse(MessageDto jsonMessage) throws JsonParseException {
        HttpRequest request;
        try {
            HttpBodyString httpBody = null;
            if (jsonMessage.getBody() != null) {
                httpBody = new HttpBodyString(jsonMessage.getBody());
            }
            request = httpRequestBuilder
                    .uri(URI.create(jsonMessage.getUrl()))
                    .params(jsonMessage.getQueryParams())
                    .method(jsonMessage.getMethod())
                    .body(httpBody)
                    .headers(new HttpHeaders(jsonMessage.getHeaders()))
                    .version(HttpVersion.of(jsonMessage.getHttpVersion()))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return request;
    }

    public static MessageDto parseJsonMessage(String jsonString) throws JsonParseException {
        Objects.requireNonNull(jsonString);
        MessageDto jsonMessage;
        try {
            jsonMessage = new ObjectMapper().readValue(jsonString, MessageDto.class);
            Objects.requireNonNull(jsonMessage);
        } catch (JsonProcessingException | NullPointerException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return jsonMessage;
    }
}
