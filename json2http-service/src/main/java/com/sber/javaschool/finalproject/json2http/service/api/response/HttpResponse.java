package com.sber.javaschool.finalproject.json2http.service.api.response;

import com.sber.javaschool.finalproject.json2http.service.http.HttpHeaders;

public interface HttpResponse {
    int statusCode();

    HttpHeaders headers();

    String body();

    String contentType();
}
