package com.sber.javaschool.finalproject.json2http.utils.httpclients;

import com.sber.javaschool.finalproject.json2http.service.api.HttpClient;
import com.sber.javaschool.finalproject.json2http.service.api.HttpRequest;
import com.sber.javaschool.finalproject.json2http.service.api.JsonToHttpRequestService;
import com.sber.javaschool.finalproject.json2http.service.api.response.HttpResponse;
import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;
import com.sber.javaschool.finalproject.json2http.service.exceptions.HttpRequestException;
import com.sber.javaschool.finalproject.json2http.service.exceptions.JsonParseException;
import com.sber.javaschool.finalproject.json2http.service.http.HttpHeaders;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class HttpClientBasedOnRestAssured implements HttpClient {

    private final JsonToHttpRequestService requestService;

    public HttpClientBasedOnRestAssured(JsonToHttpRequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public HttpResponse doRequest(HttpRequest httpRequest) {
        // TODO use the chain of responsibility (middleware)?
        if (httpRequest.method().equals("GET")) return getRequest(httpRequest);
        else if (httpRequest.method().equals("POST")) return postRequest(httpRequest);
        else if (httpRequest.method().equals("PUT")) return putRequest(httpRequest);
        else if (httpRequest.method().equals("PATCH")) return patchRequest(httpRequest);
        else if (httpRequest.method().equals("OPTIONS")) return optionsRequest(httpRequest);
        else if (httpRequest.method().equals("HEAD")) return headRequest(httpRequest);
        else if (httpRequest.method().equals("DELETE")) return deleteRequest(httpRequest);
        return null;
    }

    @Override
    public HttpResponse doRequest(MessageDto jsonString) throws HttpRequestException {
        try {
            Objects.requireNonNull(requestService);
            return doRequest(requestService.parse(jsonString));
        } catch (JsonParseException | NullPointerException e) {
            e.printStackTrace();
            throw new HttpRequestException("Error: couldn't parse message", e.getCause());
        }
    }

    private HttpResponse deleteRequest(HttpRequest httpRequest) {
        RequestSpecification rc = RestAssured.given();
        rc.headers(httpRequest.headers().map());
        httpRequest.body().ifPresent(httpBody -> rc.body(httpBody.content()));

        Response response = rc.delete(httpRequest.uri());
        return toHttpResponse(response);
    }

    private HttpResponse headRequest(HttpRequest httpRequest) {
        RequestSpecification rc = RestAssured.given();
        rc.headers(httpRequest.headers().map());

        Response response = rc.head(httpRequest.uri());
        return toHttpResponse(response);
    }

    private HttpResponse putRequest(HttpRequest httpRequest) {
        RequestSpecification rc = RestAssured.given();
        rc.headers(httpRequest.headers().map());
        httpRequest.body().ifPresent(httpBody -> rc.body(httpBody.content()));

        Response response = rc.put(httpRequest.uri());
        return toHttpResponse(response);
    }

    private HttpResponse optionsRequest(HttpRequest httpRequest) {
        RequestSpecification rc = RestAssured.given();
        rc.headers(httpRequest.headers().map());

        Response response = rc.options(httpRequest.uri());
        return toHttpResponse(response);
    }

    private HttpResponse patchRequest(HttpRequest httpRequest) {
        RequestSpecification rc = RestAssured.given();
        rc.headers(httpRequest.headers().map());
        httpRequest.body().ifPresent(httpBody -> rc.body(httpBody.content()));

        Response response = rc.patch(httpRequest.uri());
        return toHttpResponse(response);
    }

    private HttpResponse postRequest(HttpRequest httpRequest) {
        RequestSpecification rc = RestAssured.given();
        rc.headers(httpRequest.headers().map());

        httpRequest.body().ifPresent(httpBody -> rc.body(httpBody.content()));

        Response response = rc.post(httpRequest.uri());
        return toHttpResponse(response);
    }

    private HttpResponse getRequest(HttpRequest httpRequest) {
        RequestSpecification rc = RestAssured.given();
        rc.headers(httpRequest.headers().map());
        httpRequest.params().ifPresent(rc::params);

        Response response = rc.get(httpRequest.uri());
        return toHttpResponse(response);
    }

    private HttpResponse toHttpResponse(Response response) {
        return new HttpResponse() {
            @Override
            public int statusCode() {
                return response.getStatusCode();
            }

            @Override
            public HttpHeaders headers() {
                HttpHeaders httpHeaders = new HttpHeaders();
                for (Header header : response.headers()) {
                    httpHeaders.addHeader(header.getName(), header.getValue());
                }
                return httpHeaders;
            }

            @Override
            public String contentType() {
                return response.getContentType();
            }

            @Override
            public String body() {
                return response.getBody().prettyPrint();
            }
        };
    }
}
