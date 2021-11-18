package com.sber.javaschool.finalproject.json2http.service.http.request;

import com.sber.javaschool.finalproject.json2http.service.api.HttpRequest;
import com.sber.javaschool.finalproject.json2http.service.api.HttpRequestFormatter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequestFormatterImpl implements HttpRequestFormatter {
    public HttpRequestFormatterImpl() {
    }

    @Override
    public String asRequestLine(HttpRequest httpRequest) {
        List<String> reqlines = new ArrayList<>();
        String reqLine = MessageFormat.format("{0} {1} {2}",
                httpRequest.method(),
                httpRequest.uri().getPath() + '?' + httpRequest.uri().getQuery(),
                httpRequest.version().toRequestFormat());
        reqlines.add(reqLine);
        for (Map.Entry<String, String> header : httpRequest.headers().map().entrySet()) {
            reqlines.add(header.getKey() + ": " + header.getValue());
        }

        return String.join("\r\n", reqlines) + "\r\n";
    }

    @Override
    public String asCurl(HttpRequest httpRequest) {
        throw new UnsupportedOperationException();
    }
}
