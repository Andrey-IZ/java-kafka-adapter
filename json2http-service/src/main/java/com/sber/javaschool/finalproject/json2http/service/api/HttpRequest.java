package com.sber.javaschool.finalproject.json2http.service.api;

import com.sber.javaschool.finalproject.json2http.service.http.HttpHeaders;
import com.sber.javaschool.finalproject.json2http.service.http.version.HttpVersion;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

public interface HttpRequest {

    HttpHeaders headers();

    String method();

    URI uri();

    Optional<HttpBody> body();

    HttpVersion version();

    Optional<Map<String, String>> params();

    interface Builder {
        Builder uri(URI var1);

        Builder version(HttpVersion httpVersion);

        Builder headers(HttpHeaders httpHeaders);

        Builder header(String key, String value);

        Builder param(String key, String value);

        Builder params(Map<String, String> mapParams);

        Builder method(String httpVerb);

        Builder body(HttpBody httpBody);

        HttpRequest build();

//        void addMethodHandler(MethodHandler methodHandler);

        void addArgsFormatter(HttpArgsFormatter argsFormatter);
    }
}
