package com.sber.javaschool.finalproject.json2http.service.http.request;

import com.sber.javaschool.finalproject.json2http.service.api.HttpArgsFormatter;
import com.sber.javaschool.finalproject.json2http.service.api.HttpBody;
import com.sber.javaschool.finalproject.json2http.service.api.HttpRequest;
import com.sber.javaschool.finalproject.json2http.service.exceptions.InvalidHeaderValue;
import com.sber.javaschool.finalproject.json2http.service.exceptions.InvalidHttpBodyException;
import com.sber.javaschool.finalproject.json2http.service.http.HttpHeaders;
import com.sber.javaschool.finalproject.json2http.service.http.version.HttpVersion;
import com.sber.javaschool.finalproject.json2http.utils.Utils;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static com.sber.javaschool.finalproject.json2http.service.http.request.HttpRequestImpl.checkURI;

public class HttpRequestBuilder implements HttpRequest.Builder {
    private final HttpHeaders headers;
    private URI uri;
    private String method;
    private HttpBody body;
    private HttpVersion version;
    private HttpArgsFormatter argsFormatter;
    private final Map<String, String> mapParams;

    public HttpRequestBuilder(URI uri) {
        this();
        Objects.requireNonNull(uri, "uri must be non null");
        checkURI(uri);
        this.uri = uri;
    }

    public HttpRequestBuilder() {
        this.headers = new HttpHeaders();
        this.method = "GET";
        this.mapParams = new TreeMap<>();
        this.version = new HttpVersion();
    }

    @Override
    public HttpRequest.Builder uri(URI uri) {
        Objects.requireNonNull(uri, "uri must be non-null");
        checkURI(uri);
        this.uri = uri;
        return this;
    }

    @Override
    public HttpRequest.Builder version(HttpVersion version) {
        Objects.requireNonNull(version);
        this.version = version;
        return this;
    }

    @Override
    public HttpRequest.Builder headers(HttpHeaders httpHeaders) {
        try {
            this.headers.addHeaders(httpHeaders);
        } catch (InvalidHeaderValue e) {
            e.printStackTrace();
            throw new InvalidHeaderValue(e.getMessage(), e.getCause());
        }
        return this;
    }

    @Override
    public HttpRequest.Builder header(String name, String value) {
        try {
            this.headers.addHeader(name, value);
        } catch (InvalidHeaderValue e) {
            e.printStackTrace();
            throw new InvalidHeaderValue(e.getMessage(), e.getCause());
        }
        return this;
    }

    @Override
    public HttpRequest.Builder param(String key, String value) {
        mapParams.put(key, value);
        return this;
    }

    @Override
    public HttpRequest.Builder params(Map<String, String> mapParams) {
        if (mapParams != null)
            this.mapParams.putAll(mapParams);
        return this;
    }

    @Override
    public HttpRequest.Builder method(String httpVerb) {
        Objects.requireNonNull(method);

        if (method.equals("")) {
            throw new InvalidHttpBodyException("invalid method <empty string>");
        } else if (Utils.isNotValidName(method)) {
            throw new InvalidHttpBodyException("invalid method \"" + method.replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t") + "\"");
        }
        this.method = httpVerb.toUpperCase();
        return this;
    }

    @Override
    public HttpRequest.Builder body(HttpBody httpBody) {
        this.body = httpBody;
        return this;
    }

    @Override
    public HttpRequest build() {
        if (this.uri == null) {
            throw new IllegalStateException("uri is null");
        }
        //TODO chain of responsibilities
//        for (MethodHandler handler){
//              handler.modifyHeaders(this);
//        }
        headers.addHeader("host", this.uri().getHost());
//        headers.addHeader("content-length", String.valueOf(this.body.contentLength()));
        if (argsFormatter != null && !this.params().isEmpty()) {
            this.uri = URI.create(this.uri.toString() + "?" + argsFormatter.formatArgs(this.params()));
        }
        return new HttpRequestImpl(this);
    }

    @Override
    public void addArgsFormatter(HttpArgsFormatter argsFormatter) {
        this.argsFormatter = argsFormatter;
    }

    URI uri() {
        return this.uri;
    }

    Map<String, String> params() {
        return this.mapParams;
    }

    HttpHeaders httpHeaders() {
        return this.headers;
    }

    String method() {
        return this.method;
    }

    HttpBody body() {
        return this.body;
    }

    HttpVersion version() {
        return this.version;
    }
}
