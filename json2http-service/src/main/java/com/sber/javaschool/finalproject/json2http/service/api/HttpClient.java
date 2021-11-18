package com.sber.javaschool.finalproject.json2http.service.api;

import com.sber.javaschool.finalproject.json2http.service.api.response.HttpResponse;
import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;
import com.sber.javaschool.finalproject.json2http.service.exceptions.HttpRequestException;

public interface HttpClient {
    HttpResponse doRequest(HttpRequest httpRequest) throws HttpRequestException;

    HttpResponse doRequest(MessageDto jsonString) throws HttpRequestException;
}
