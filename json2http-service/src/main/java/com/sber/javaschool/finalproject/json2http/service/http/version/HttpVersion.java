package com.sber.javaschool.finalproject.json2http.service.http.version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpVersion extends ProtocolVersion {

    public HttpVersion() {
        super(HTTP, 1, 1);
    }

    public HttpVersion(final int major, final int minor) {
        super(HTTP, major, minor);
    }

    public HttpVersion(String protocolName, int major, int minor) {
        super(protocolName, major, minor);
    }

    /**
     * The protocol name.
     */
    public static final String HTTP = "HTTP";

    /**
     * HTTP protocol version 0.9
     */
    public static final HttpVersion HTTP_0_9 = new HttpVersion(0, 9);

    /**
     * HTTP protocol version 1.0
     */
    public static final HttpVersion HTTP_1_0 = new HttpVersion(1, 0);

    /**
     * HTTP protocol version 1.1
     */
    public static final HttpVersion HTTP_1_1 = new HttpVersion(1, 1);

    /**
     * HTTP protocol version 2.0
     */
    public static final HttpVersion HTTP_2_0 = new HttpVersion(2, 0);

    /**
     * HTTP protocol version 3.0
     */
    public static final HttpVersion HTTP_3_0 = new HttpVersion(3, 0);

    public static HttpVersion of(String versionString) {
        Pattern pattern = Pattern.compile("(\\d+)(\\.(\\d+))?");
        Matcher matcher = pattern.matcher(versionString);
        if (matcher.find()) {
            int major = Integer.parseInt(matcher.group(1)); // 1
            int minor = Integer.parseInt(matcher.group(3)); // .1
            return new HttpVersion(major, minor);
        }
        return new HttpVersion();
    }
}
