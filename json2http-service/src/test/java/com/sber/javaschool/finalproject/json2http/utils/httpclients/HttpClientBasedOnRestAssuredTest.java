package com.sber.javaschool.finalproject.json2http.utils.httpclients;

import com.sber.javaschool.finalproject.json2http.service.JsonToHttpRequestServiceImpl;
import com.sber.javaschool.finalproject.json2http.service.api.HttpRequest;
import com.sber.javaschool.finalproject.json2http.service.api.JsonToHttpRequestService;
import com.sber.javaschool.finalproject.json2http.service.exceptions.JsonParseException;
import com.sber.javaschool.finalproject.json2http.service.http.request.HttpRequestBuilder;
import com.sber.javaschool.finalproject.json2http.utils.HttpArgsQueryFormatter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpClientBasedOnRestAssuredTest {

    private static HttpRequest.Builder httpRequestBuilder;

    @BeforeAll
    private static void setUpAll() {
        httpRequestBuilder = new HttpRequestBuilder();
        httpRequestBuilder.addArgsFormatter(new HttpArgsQueryFormatter());
    }

    @Test
    public void doGetRequest() throws JsonParseException {
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

        HttpClientBasedOnRestAssured client = new HttpClientBasedOnRestAssured(service);
        System.out.println(client.doRequest(httpRequest));
    }

    @Test
    public void doPostRequest() throws JsonParseException {
        var json = "{\n" +
                "  \"v\": \"1.1\",\n" +
                "  \"method\": \"post\",\n" +
                "  \"url\":\"https://postman-echo.com/post\",\n" +
                "  \"headers\": {\n" +
                "    \"accept\": \"*/*\",\n" +
                "    \"accept-encoding\": \"gzip, deflate, br\",\n" +
                "    \"accept-language\": \"en-US,en;q=0.9,ru;q=0.8\",\n" +
                "    \"content-Type\": \"application/x-www-form-urlencoded\",\n" +
                "    \"connection\": \"keep-alive\",\n" +
                "    \"sec-ch-ua\":  \"\\\"Google Chrome\\\";v=\\\"95\\\", \\\"Chromium\\\";v=\\\"95\\\", \\\";Not A Brand\\\";v=\\\"99\\\"\",\n" +
                "    \"sec-ch-ua-mobile\": \"?0\",\n" +
                "    \"sec-ch-ua-platform\": \"Linux\",\n" +
                "    \"sec-fetch-dest\": \"empty\",\n" +
                "    \"sec-fetch-mode\": \"cors\",\n" +
                "    \"sec-fetch-site\": \"same-site\",\n" +
                "    \"user-agent\": \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36\"\n" +
                "  },\n" +
                "  \"body\": \"{\\\"say\\\": \\\"Hi\\\",\\\"to\\\": \\\"Mom\\\"}\"\n" +
                "}";

        JsonToHttpRequestService service = new JsonToHttpRequestServiceImpl(httpRequestBuilder);
        var httpRequest = service.parse(json);
        assertNotNull(httpRequest);

        HttpClientBasedOnRestAssured client = new HttpClientBasedOnRestAssured(service);
        System.out.println(client.doRequest(httpRequest));
    }

}