package org.example;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class HttpUtils {
    public static String getRequest(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        StringBuilder reqBody = new StringBuilder();

        int curr = inputStream.read();
        while (curr != -1) {
            reqBody.append((char) curr);
            curr = inputStream.read();
        }
        inputStream.close();

        return reqBody.toString();
    }

    public static void sendResponse(int status, String res, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(status, res.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(res.getBytes());
        outputStream.close();
    }
}
