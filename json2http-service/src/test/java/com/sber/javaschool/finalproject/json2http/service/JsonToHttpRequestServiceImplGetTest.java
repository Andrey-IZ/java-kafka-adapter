package com.sber.javaschool.finalproject.json2http.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sber.javaschool.finalproject.json2http.service.api.HttpRequest;
import com.sber.javaschool.finalproject.json2http.service.api.JsonToHttpRequestService;
import com.sber.javaschool.finalproject.json2http.service.dto.JsonDto;
import com.sber.javaschool.finalproject.json2http.service.exceptions.JsonParseException;
import com.sber.javaschool.finalproject.json2http.service.http.request.HttpRequestBuilder;
import com.sber.javaschool.finalproject.json2http.service.http.request.HttpRequestFormatterImpl;
import com.sber.javaschool.finalproject.json2http.utils.HttpArgsQueryFormatter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonToHttpRequestServiceImplGetTest {

    private static String jsonGet;
    private static ObjectMapper mapper;
    private static HttpRequest.Builder httpRequestBuilder;

    @BeforeAll
    private static void setUpAll() {
        mapper = new ObjectMapper();
        httpRequestBuilder = new HttpRequestBuilder();
        httpRequestBuilder.addArgsFormatter(new HttpArgsQueryFormatter());

//        jsonGet = loadFile("samples/get1.json");
        jsonGet = "{\n" +
                "  \"v\": \"1.1\",\n" +
                "  \"method\": \"get\",\n" +
                "  \"url\":\"https://postman-echo.com/get\",\n" +
                "  \"params\": {\n" +
                "    \"foo1\": \"bar1\",\n" +
                "    \"foo2\": \"bar2\"\n" +
                "  },\n" +
                "  \"headers\": {\n" +
                "    \"accept\": \"*/*\",\n" +
                "    \"accept-encoding\": \"gzip, deflate, br\",\n" +
                "    \"accept-language\": \"en-US,en;q=0.9,ru;q=0.8\",\n" +
                "    \"content-Type\": \"application/json; charset=utf-8\",\n" +
                "    \"connection\": \"keep-alive\",\n" +
                "    \"sec-ch-ua\":  \"\\\"Google Chrome\\\";v=\\\"95\\\", \\\"Chromium\\\";v=\\\"95\\\", \\\";Not A Brand\\\";v=\\\"99\\\"\",\n" +
                "    \"sec-ch-ua-mobile\": \"?0\",\n" +
                "    \"sec-ch-ua-platform\": \"Linux\",\n" +
                "    \"sec-fetch-dest\": \"empty\",\n" +
                "    \"sec-fetch-mode\": \"cors\",\n" +
                "    \"sec-fetch-site\": \"same-site\",\n" +
                "    \"user-agent\": \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36\"\n" +
                "  }\n" +
                "}";
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void parse_simple_get_request() throws JsonParseException {
        var json = "{\n" +
                "  \"v\": \"1.1\",\n" +
                "  \"method\": \"get\",\n" +
                "  \"url\":\"https://postman-echo.com/get\",\n" +
                "  \"params\": {\n" +
                "    \"foo1\": \"bar1\",\n" +
                "    \"foo2\": \"bar2\"\n" +
                "  },\n" +
                "  \"headers\": {\n" +
                "    \"accept\": \"*/*\",\n" +
                "    \"accept-encoding\": \"gzip, deflate, br\",\n" +
                "    \"accept-language\": \"en-US,en;q=0.9,ru;q=0.8\",\n" +
                "    \"connection\": \"keep-alive\",\n" +
                "    \"sec-ch-ua\":  \"\\\"Google Chrome\\\";v=\\\"95\\\", \\\"Chromium\\\";v=\\\"95\\\", \\\";Not A Brand\\\";v=\\\"99\\\"\",\n" +
                "    \"sec-ch-ua-mobile\": \"?0\",\n" +
                "    \"sec-ch-ua-platform\": \"Linux\",\n" +
                "    \"sec-fetch-dest\": \"empty\",\n" +
                "    \"sec-fetch-mode\": \"cors\",\n" +
                "    \"sec-fetch-site\": \"same-site\",\n" +
                "    \"user-agent\": \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36\"\n" +
                "  }\n" +
                "}";
        JsonToHttpRequestService service = new JsonToHttpRequestServiceImpl(httpRequestBuilder);
        var httpRequest = service.parse(json);
        assertNotNull(httpRequest);
        assertEquals("HTTP/1.1", httpRequest.version().toRequestFormat());
        assertEquals("GET", httpRequest.method());

        Map<String, String> headersExpected = new TreeMap<>() {{
            put("host", "postman-echo.com");  // added automatically
            put("accept", "*/*");
            put("accept-encoding", "gzip, deflate, br");
            put("accept-language", "en-US,en;q=0.9,ru;q=0.8");
            put("connection", "keep-alive");
            put("sec-ch-ua", "\"Google Chrome\";v=\"95\", \"Chromium\";v=\"95\", \";Not A Brand\";v=\"99\"");
            put("sec-ch-ua-mobile", "?0");
            put("sec-ch-ua-platform", "Linux");
            put("sec-fetch-dest", "empty");
            put("sec-fetch-mode", "cors");
            put("sec-fetch-site", "same-site");
            put("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36");
        }};
        Map<String, String> paramsExpected = new TreeMap<>() {{
            put("foo1", "bar1");
            put("foo2", "bar2");
        }};
        assertTrue(httpRequest.params().isPresent());
        assertEquals(paramsExpected, httpRequest.params().get());
        assertEquals(headersExpected, httpRequest.headers().map());
        assertEquals(URI.create("https://postman-echo.com/get?foo1=bar1&foo2=bar2"), httpRequest.uri());
        assertEquals(Optional.empty(), httpRequest.body());
    }

    @Test
    public void parse_get_request_line_test() throws JsonParseException {
        var json = "{\n" +
                "  \"v\": \"1.1\",\n" +
                "  \"method\": \"get\",\n" +
                "  \"url\":\"https://postman-echo.com/get\",\n" +
                "  \"params\": {\n" +
                "    \"foo1\": \"bar1\",\n" +
                "    \"foo2\": \"bar2\"\n" +
                "  },\n" +
                "  \"headers\": {\n" +
                "    \"accept\": \"*/*\",\n" +
                "    \"accept-encoding\": \"gzip, deflate, br\",\n" +
                "    \"accept-language\": \"en-US,en;q=0.9,ru;q=0.8\",\n" +
                "    \"connection\": \"keep-alive\",\n" +
                "    \"sec-ch-ua\":  \"\\\"Google Chrome\\\";v=\\\"95\\\", \\\"Chromium\\\";v=\\\"95\\\", \\\";Not A Brand\\\";v=\\\"99\\\"\",\n" +
                "    \"sec-ch-ua-mobile\": \"?0\",\n" +
                "    \"sec-ch-ua-platform\": \"Linux\",\n" +
                "    \"sec-fetch-dest\": \"empty\",\n" +
                "    \"sec-fetch-mode\": \"cors\",\n" +
                "    \"sec-fetch-site\": \"same-site\",\n" +
                "    \"user-agent\": \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36\"\n" +
                "  }\n" +
                "}";
        JsonToHttpRequestService service = new JsonToHttpRequestServiceImpl(httpRequestBuilder);
        HttpRequest httpRequest = service.parse(json);
        assertNotNull(httpRequest);
        var requestString = new HttpRequestFormatterImpl().asRequestLine(httpRequest);
        var requestExpected =
                "GET /get?foo1=bar1&foo2=bar2 HTTP/1.1\r\n" +
                        "host: postman-echo.com\r\n" +
                        "accept: */*\r\n" +
                        "accept-encoding: gzip, deflate, br\r\n" +
                        "accept-language: en-US,en;q=0.9,ru;q=0.8\r\n" +
                        "connection: keep-alive\r\n" +
                        "sec-ch-ua: \"Google Chrome\";v=\"95\", \"Chromium\";v=\"95\", \";Not A Brand\";v=\"99\"\r\n" +
                        "sec-ch-ua-mobile: ?0\r\n" +
                        "sec-ch-ua-platform: Linux\r\n" +
                        "sec-fetch-dest: empty\r\n" +
                        "sec-fetch-mode: cors\r\n" +
                        "sec-fetch-site: same-site\r\n" +
                        "user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36\r\n" +
                        "\r\n";
        var linesExpected = requestExpected.split("\r\n");
        var linesActual = requestString.split("\r\n");

        assertEquals(linesExpected.length, linesActual.length);
        assertEquals(linesExpected[0], linesActual[0]);
        assertEquals(linesExpected[linesExpected.length - 1], linesActual[linesActual.length - 1]);

        var setHeadersExpected = new HashSet<>(List.of(linesExpected));
        var setHeadersActual = new HashSet<>(List.of(linesActual));
        assertEquals(setHeadersExpected, setHeadersActual);
    }

    @Test
    public void parse_get_check_version() throws JsonProcessingException, JsonParseException {
        var jsonDto = mapper.readValue(jsonGet, JsonDto.class);
        jsonDto.setHttpVersion("");
        String jsonString = mapper.writeValueAsString(jsonDto);
        JsonToHttpRequestService service = new JsonToHttpRequestServiceImpl(httpRequestBuilder);
        var request = service.parse(jsonString);
        assertNotNull(request);
        assertEquals("1.1", request.version().asString());
        assertEquals("HTTP/1.1", request.version().toRequestFormat());
    }
}