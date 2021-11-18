package com.sber.javaschool.finalproject.json2http.service.http.body;

import com.sber.javaschool.finalproject.json2http.service.api.HttpBody;

public class HttpBodyString implements HttpBody {

    protected final String content;

    public HttpBodyString() {
        content = "";
    }

    public HttpBodyString(String content) {
        this.content = content;
    }

    @Override
    public String content() {
        return content;
    }

    @Override
    public long contentLength() {
        if (content == null)
            return 0;
        return content.getBytes().length;
    }
}
