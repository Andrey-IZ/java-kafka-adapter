package com.sber.javaschool.finalproject.json2http.service.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class JsonMessageTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void deserialize_get() throws JsonProcessingException {
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
        ObjectMapper om = new ObjectMapper();
        JsonDto message = om.readValue(json, JsonDto.class);
        assertNotNull(message);
        assertEquals("https://postman-echo.com/get", message.getUrl());

        Map<String, String> headersExpected = new TreeMap<>() {{
            put("accept", "*/*");
            put("accept-encoding", "gzip, deflate, br");
            put("accept-language", "en-US,en;q=0.9,ru;q=0.8");
            put("content-Type", "application/json; charset=utf-8");
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
        assertEquals(headersExpected, message.getHeaders());
        assertEquals("1.1", message.getHttpVersion());
        assertEquals(paramsExpected, message.getQueryParams());
        assertNull(message.getBody());
    }

    @Test
    public void deserialize_post_multipart() throws JsonProcessingException {
        var json = "{\n" +
                "  \"v\": \"1.1\",\n" +
                "  \"method\": \"post\",\n" +
                "  \"url\":\"https://postman-echo.com/post\",\n" +
                "  \"headers\": {\n" +
                "    \"accept\": \"*/*\",\n" +
                "    \"accept-encoding\": \"gzip, deflate, br\",\n" +
                "    \"accept-language\": \"en-US,en;q=0.9,ru;q=0.8\",\n" +
                "    \"content-Type\": \"multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0g\",\n" +
                "    \"connection\": \"keep-alive\",\n" +
                "    \"sec-ch-ua\":  \"\\\"Google Chrome\\\";v=\\\"95\\\", \\\"Chromium\\\";v=\\\"95\\\", \\\";Not A Brand\\\";v=\\\"99\\\"\",\n" +
                "    \"sec-ch-ua-mobile\": \"?0\",\n" +
                "    \"sec-ch-ua-platform\": \"Linux\",\n" +
                "    \"sec-fetch-dest\": \"empty\",\n" +
                "    \"sec-fetch-mode\": \"cors\",\n" +
                "    \"sec-fetch-site\": \"same-site\",\n" +
                "    \"user-agent\": \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36\"\n" +
                "  },\n" +
                "  \"body\": \"--boundary\\nContent-Disposition: form-data; name=\\\"field1\\\"\\n\\nvalue1\\n--boundary\\nContent-Disposition: form-data; name=\\\"field2\\\"; filename=\\\"example.txt\\\"\\n\\nvalue2\\n--boundary--\"\n" +
                "}";
        ObjectMapper om = new ObjectMapper();
        JsonDto message = om.readValue(json, JsonDto.class);
        assertNotNull(message);
        assertEquals("https://postman-echo.com/post", message.getUrl());

        Map<String, String> headersExpected = new TreeMap<>() {{
            put("accept", "*/*");
            put("accept-encoding", "gzip, deflate, br");
            put("accept-language", "en-US,en;q=0.9,ru;q=0.8");
            put("content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0g");
            put("connection", "keep-alive");
            put("sec-ch-ua", "\"Google Chrome\";v=\"95\", \"Chromium\";v=\"95\", \";Not A Brand\";v=\"99\"");
            put("sec-ch-ua-mobile", "?0");
            put("sec-ch-ua-platform", "Linux");
            put("sec-fetch-dest", "empty");
            put("sec-fetch-mode", "cors");
            put("sec-fetch-site", "same-site");
            put("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36");
        }};
        assertEquals(headersExpected, message.getHeaders());
        assertEquals("1.1", message.getHttpVersion());
        assertNull(message.getQueryParams());
        String expectedBody = "--boundary\nContent-Disposition: form-data; name=\"field1\"\n\nvalue1\n--boundary\n" +
                "Content-Disposition: form-data; name=\"field2\"; filename=\"example.txt\"\n\nvalue2\n--boundary--";
        assertEquals(expectedBody, message.getBody());
    }
}