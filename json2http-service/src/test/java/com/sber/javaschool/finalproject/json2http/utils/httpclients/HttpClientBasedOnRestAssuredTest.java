package com.sber.javaschool.finalproject.json2http.utils.httpclients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sber.javaschool.finalproject.json2http.service.JsonToHttpRequestServiceImpl;
import com.sber.javaschool.finalproject.json2http.service.api.HttpRequest;
import com.sber.javaschool.finalproject.json2http.service.api.response.HttpResponse;
import com.sber.javaschool.finalproject.json2http.service.dto.MessageDto;
import com.sber.javaschool.finalproject.json2http.service.exceptions.HttpRequestException;
import com.sber.javaschool.finalproject.json2http.service.http.request.HttpRequestBuilder;
import com.sber.javaschool.finalproject.json2http.utils.HttpArgsQueryFormatter;
import com.sber.javaschool.finalproject.json2http.utils.TestUtils;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest(proxyMode = true)
class HttpClientBasedOnRestAssuredTest {

    private static HttpRequest.Builder httpRequestBuilder;

    private final static String body = "\"{\\\"say\\\": \\\"Hi\\\",\\\"to\\\": \\\"Mom\\\"}\"";
    private static ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        httpRequestBuilder = new HttpRequestBuilder();
        httpRequestBuilder.addArgsFormatter(new HttpArgsQueryFormatter());
    }

    @BeforeAll
    private static void setUpAll() {
        mapper = new ObjectMapper();
    }

    @Test
    public void test_get_request_with_params() throws IOException, HttpRequestException, JSONException {
        MessageDto messageDto = getMessageDto("samples/get1.json");
        assertNotNull(messageDto);
        var host = getHost(messageDto);
        assertNotNull(host);

        var queryParams = Map.of(
                "foo1", equalTo("bar1"),
                "foo2", equalTo("bar2")
        );
        givenThat(get(urlPathEqualTo("/get"))
                .withQueryParams(queryParams)
                .withHost(equalTo(host))        // proxy mode = true
                .withHeader("Host", equalTo(host))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(body)
                ));

        HttpResponse httpResponse = makeHttpRequest(messageDto);

        assertEquals(200, httpResponse.statusCode());
        JSONAssert.assertEquals(body, httpResponse.body(), JSONCompareMode.STRICT);
    }

    @Test
    public void test_post_request_with_json_body() throws IOException, HttpRequestException {
        MessageDto messageDto = getMessageDto("samples/post_json.json");
        assertNotNull(messageDto);
        var host = getHost(messageDto);
        var requestBody = messageDto.getBody();
        var contentLength = requestBody.length();
        assertNotNull(host);

        givenThat(post(urlPathEqualTo("/post"))
                .withHost(equalTo(host))        // proxy mode = true
                .withRequestBody(equalToJson(requestBody))
                .withHeader("Content-Type", equalToIgnoreCase("application/json"))
                .withHeader("Host", equalTo(host))
                .willReturn(ok()));

        HttpResponse httpResponse = makeHttpRequest(messageDto);

        assertEquals(200, httpResponse.statusCode());
        assertNoBody(httpResponse);
    }

    @Test
    public void test_put_request_created_with_json_body() throws IOException, HttpRequestException {
        MessageDto messageDto = getMessageDto("samples/put1.json");
        assertNotNull(messageDto);
        var host = getHost(messageDto);
        var requestBody = messageDto.getBody();
        assertNotNull(host);

        givenThat(put(urlPathEqualTo("/put"))
                .withHost(equalTo(host))        // proxy mode = true
                .withRequestBody(equalToJson(requestBody))
                .withHeader("Content-Type", equalToIgnoreCase("application/json"))
                .withHeader("Host", equalTo(host))
                .willReturn(aResponse()
                        .withStatus(201)
                ));

        HttpResponse httpResponse = makeHttpRequest(messageDto);

        assertEquals(201, httpResponse.statusCode());
        assertNotNull(httpResponse.body());
        assertNoBody(httpResponse);
    }

    @Test
    public void test_put_request_modified_with_json_body() throws IOException, HttpRequestException {
        MessageDto messageDto = getMessageDto("samples/put1.json");
        assertNotNull(messageDto);
        var host = getHost(messageDto);
        var requestBody = messageDto.getBody();
        assertNotNull(host);

        givenThat(put(urlPathEqualTo("/put"))
                .withHost(equalTo(host))        // proxy mode = true
                .withRequestBody(equalToJson(requestBody))
                .withHeader("Content-Type", equalToIgnoreCase("application/json"))
                .withHeader("Host", equalTo(host))
                .willReturn(aResponse()
                        .withStatus(204)
                ));

        HttpResponse httpResponse = makeHttpRequest(messageDto);

        assertEquals(204, httpResponse.statusCode());
        assertNotNull(httpResponse.body());
        assertNoBody(httpResponse);
    }

    @Test
    public void test_head_request_with_params() throws IOException, HttpRequestException {
        MessageDto messageDto = getMessageDto("samples/head1.json");
        assertNotNull(messageDto);
        var host = getHost(messageDto);
        assertNotNull(host);

        var queryParams = Map.of(
                "foo1", equalTo("bar1"),
                "foo2", equalTo("bar2")
        );
        givenThat(head(urlPathEqualTo("/head"))
                .withQueryParams(queryParams)
                .withHost(equalTo(host))        // proxy mode = true
                .withHeader("Host", equalTo(host))
                .willReturn(aResponse()
                        .withStatus(200)
                ));

        HttpResponse httpResponse = makeHttpRequest(messageDto);

        assertEquals(200, httpResponse.statusCode());
        assertNoBody(httpResponse);
    }

    @Test
    public void test_options_request_no_body() throws IOException, HttpRequestException {
        MessageDto messageDto = getMessageDto("samples/head1.json");
        assertNotNull(messageDto);
        var host = getHost(messageDto);
        assertNotNull(host);

        var queryParams = Map.of(
                "foo1", equalTo("bar1"),
                "foo2", equalTo("bar2")
        );
        givenThat(head(urlPathEqualTo("/head"))
                .withQueryParams(queryParams)
                .withHost(equalTo(host))        // proxy mode = true
                .withHeader("Host", equalTo(host))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Length", "0")
                        .withHeader("Allow", "GET,POST,DELETE,OPTIONS")
                ));

        HttpResponse httpResponse = makeHttpRequest(messageDto);
        assertEquals(200, httpResponse.statusCode());
        assertNoBody(httpResponse);
        // check Allow header
        assertEquals("GET,POST,DELETE,OPTIONS", httpResponse.headers().value("Allow"));
    }

    private String getHost(MessageDto messageDto) {
        return URI.create(messageDto.getUrl()).getHost();
    }

    private MessageDto getMessageDto(String s) throws IOException {
        return mapper.readValue(TestUtils.loadFile(s), MessageDto.class);
    }

    private HttpResponse makeHttpRequest(MessageDto messageDto) throws HttpRequestException {
        HttpClientBasedOnRestAssured client = new HttpClientBasedOnRestAssured(new JsonToHttpRequestServiceImpl(httpRequestBuilder));
        HttpResponse httpResponse = client.doRequest(messageDto);
        assertNotNull(httpResponse);
        return httpResponse;
    }

    private void assertNoBody(HttpResponse httpResponse) {
        assertNotNull(httpResponse.body());
        assertTrue(httpResponse.body().isEmpty());
    }
}