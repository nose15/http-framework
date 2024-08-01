package org.framework.http;

import com.sun.net.httpserver.HttpExchange;
import org.framework.http.request.Request;
import org.framework.http.request.RequestBuilder;
import org.framework.router.HttpMethod;

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

    public static void sendResponse(int status, String res, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(status, res.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(res.getBytes());
        outputStream.close();
    }
}
