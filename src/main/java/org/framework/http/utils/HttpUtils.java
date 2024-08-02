package org.framework.http.utils;

import com.sun.net.httpserver.HttpExchange;
import org.framework.http.request.Request;
import org.framework.http.request.RequestBuilder;
import org.framework.http.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class HttpUtils {
    public static Request getRequest(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        StringBuilder reqBody = new StringBuilder();

        int curr = inputStream.read();
        while (curr != -1) {
            reqBody.append((char) curr);
            curr = inputStream.read();
        }
        inputStream.close();

        return new RequestBuilder()
                .setBody(reqBody.toString())
                .setMethod(HttpMethod.valueOf(exchange.getRequestMethod()))
                .setUri(exchange.getRequestURI())
                .addHeaders(exchange.getRequestHeaders())
                .build();
    }

    public static void sendResponse(Response response, HttpExchange exchange) throws IOException {
        String body = response.getBody();
        exchange.sendResponseHeaders(response.getStatusCode(), body.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(body.getBytes());
        outputStream.close();
    }
}
