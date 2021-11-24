package com.sber.javaschool.finalproject.json2http.service.api.response;

import com.sber.javaschool.finalproject.json2http.service.http.HttpHeaders;

import java.text.MessageFormat;

public interface HttpResponse {
    int statusCode();

    HttpHeaders headers();

    String body();

    String contentType();

    default String toPrettyPrintString() {
        return MessageFormat.format("status={0}," +
                        " headers={1}, " +
                        " contentType={2}," +
                        " body={3}",
                headers(), statusCode(), contentType(), body());
    }
}
