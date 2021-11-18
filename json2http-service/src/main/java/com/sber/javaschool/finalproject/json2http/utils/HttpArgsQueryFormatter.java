package com.sber.javaschool.finalproject.json2http.utils;

import com.sber.javaschool.finalproject.json2http.service.api.HttpArgsFormatter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class HttpArgsQueryFormatter implements HttpArgsFormatter {
    @Override
    public String formatArgs(Map<String, String> args) {
        Objects.requireNonNull(args);
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String> param : args.entrySet()) {
            if (params.length() != 0) params.append('&');
            params.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
            params.append("=");
            params.append(URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8));
        }

        return params.toString();
    }
}
