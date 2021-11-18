package com.sber.javaschool.finalproject.json2http.service.api;

public interface HttpRequestFormatter {
    String asRequestLine(HttpRequest httpRequest);

    String asCurl(HttpRequest httpRequest) throws UnsupportedOperationException;
}
