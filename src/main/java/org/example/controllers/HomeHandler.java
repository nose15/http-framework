package org.example.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HomeHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String req = HttpUtils.getRequest(exchange);
        HttpUtils.sendResponse(200, req, exchange);
    }
}