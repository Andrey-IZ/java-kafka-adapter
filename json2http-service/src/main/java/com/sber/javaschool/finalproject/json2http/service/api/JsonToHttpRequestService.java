package com.sber.javaschool.finalproject.json2http.service.api;

import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;
import com.sber.javaschool.finalproject.json2http.service.exceptions.JsonParseException;

public interface JsonToHttpRequestService {
    HttpRequest parse(String jsonString) throws JsonParseException;

    HttpRequest parse(MessageDto jsonString) throws JsonParseException;
}
