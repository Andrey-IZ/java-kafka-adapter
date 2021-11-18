package com.sber.javaschool.finalproject.json2http.utils.httpclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class HttpClientOnSockets {

    public static String send(String host, String request) throws IOException {
        return send(host, 80, request);
    }

    public static String send(String hostname, int port, String request) throws IOException {
        try (Socket socket = new Socket(InetAddress.getByName(hostname), port);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            // send an HTTP request to the web server
            pw.print(request);
            pw.flush();

            // read the response
            return readResponse(in);
        }
    }

    private static String readResponse(BufferedReader in) throws IOException {
        boolean loop = true;
        StringBuilder sb = new StringBuilder(8096);
        while (loop) {
            if (in.ready()) {
                int i = 0;
                while (i != -1) {
                    i = in.read();
                    sb.append((char) i);
                }
                loop = false;
            }
        }
        return sb.toString();
    }
}
