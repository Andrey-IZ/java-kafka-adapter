package com.sber.javaschool.finalproject.json2http.service.http.request;

import com.sber.javaschool.finalproject.json2http.service.api.HttpBody;
import com.sber.javaschool.finalproject.json2http.service.api.HttpRequest;
import com.sber.javaschool.finalproject.json2http.service.exceptions.InvalidURI;
import com.sber.javaschool.finalproject.json2http.service.http.HttpHeaders;
import com.sber.javaschool.finalproject.json2http.service.http.version.HttpVersion;

import java.net.URI;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class HttpRequestImpl implements HttpRequest {
    private final HttpVersion version;
    private final HttpHeaders headers;
    private final URI uri;
    private final String method;
    private final HttpBody body;
    private final Map<String, String> params;

    public HttpRequestImpl(HttpRequestBuilder builder) {
        this.uri = builder.uri();
        version = builder.version();
        headers = new HttpHeaders(builder.httpHeaders().map());
        method = builder.method() == null ? "GET" : builder.method();
        body = builder.body();
        params = builder.params();
    }

    static void checkURI(URI uri) {
        if (uri == null) {
            throw new InvalidURI("URI doesn't initialized");
        }
        String scheme = uri.getScheme();
        if (scheme == null) {
            throw new InvalidURI("URI with undefined scheme");
        } else {
            scheme = scheme.toLowerCase(Locale.US);
            if (!scheme.equals("https") && !scheme.equals("http")) {
                throw new InvalidURI("invalid URI scheme " + scheme);
            } else if (uri.getHost() == null) {
                throw new InvalidURI("unsupported URI %s" + uri);
            }
        }
    }

    @Override
    public HttpHeaders headers() {
        return headers;
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public URI uri() {
        return uri;
    }

    @Override
    public Optional<HttpBody> body() {
        if (this.body == null || this.body.contentLength() == 0) {
            return Optional.empty();
        }
        return Optional.of(this.body);
    }

    @Override
    public HttpVersion version() {
        return version;
    }

    @Override
    public Optional<Map<String, String>> params() {
        return Optional.of(params);
    }

    public HttpVersion getVersion() {
        return this.version;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public URI getUri() {
        return this.uri;
    }

    public String getMethod() {
        return this.method;
    }

    public HttpBody getBody() {
        return this.body;
    }

    public Map<String, String> getParams() {
        return this.params;
    }
}
