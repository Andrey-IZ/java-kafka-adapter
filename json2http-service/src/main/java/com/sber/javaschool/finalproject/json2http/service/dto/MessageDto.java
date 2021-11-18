package com.sber.javaschool.finalproject.json2http.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class MessageDto {
    @JsonProperty("v")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String httpVersion;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String method;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url;

    @JsonProperty("params")
    private Map<String, String> queryParams;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> headers;

    private String body;
}
