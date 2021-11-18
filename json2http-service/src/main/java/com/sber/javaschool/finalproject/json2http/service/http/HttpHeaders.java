package com.sber.javaschool.finalproject.json2http.service.http;

import com.sber.javaschool.finalproject.json2http.service.exceptions.InvalidHeaderValue;
import com.sber.javaschool.finalproject.json2http.utils.Utils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public final class HttpHeaders {
    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public HttpHeaders(Map<String, String> headers) {
        this();
        this.headers.putAll(headers);
    }

    public String value(String name) {
        return this.headers.getOrDefault(name, null);
    }

    public Map<String, String> map() {
        return new TreeMap<>(this.headers);
    }

    public String toString() {
        return super.toString() + " { " +
                this.map() +
                " }";
    }

    public void clear() {
        this.headers.clear();
    }

    public void addHeader(String name, String value) throws InvalidHeaderValue {
        checkNameAndValue(name, value);
        headers.put(name.toLowerCase(), value);
    }

    private void checkNameAndValue(String name, String value) throws InvalidHeaderValue {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(value, "value");
        if (Utils.isNotValidName(name)) {
            throw new InvalidHeaderValue(MessageFormat.format("invalid header name: \"{0}\"", name));
        } else if (Utils.isNotValidValue(value)) {
            throw new InvalidHeaderValue(MessageFormat.format("invalid header value: \"{0}\"", value));
        }
    }

    public void addHeaders(HttpHeaders httpHeaders) throws InvalidHeaderValue {
        for (Map.Entry<String, String> entry : httpHeaders.map().entrySet()) {
            addHeader(entry.getKey(), entry.getValue());
        }
        headers.putAll(httpHeaders.map());
    }

}
