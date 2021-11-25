package com.sber.javaschool.finalproject.json2http.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static String loadFile(String fileName) throws IOException {
        var classloader = Thread.currentThread().getContextClassLoader();
        var is = classloader.getResourceAsStream(fileName);
        List<String> lines = new ArrayList<>();
        if (is == null)
            return String.join("", lines);
        var streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        for (String line; (line = reader.readLine()) != null; ) {
            lines.add(line);
        }
        return String.join("", lines);
    }
}
