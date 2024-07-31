package org.example.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.HttpUtils;
import org.example.router.Router;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class ApiController implements HttpHandler {
    private final Router router;

    ApiController() {
        this.router = new Router(this.getClass().getDeclaredMethods());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRequestMethod());
        String req = HttpUtils.getRequest(exchange);
        String res;

        try {
            Method method = router.dispatch(exchange.getRequestURI());
            res = method.invoke(this, req).toString();
            HttpUtils.sendResponse(200, res, exchange);
        } catch (IllegalArgumentException e) {
            res = "Not found";
            HttpUtils.sendResponse(404, res, exchange);
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.out.println(e);
            res = "Internal server error";
            HttpUtils.sendResponse(500, res, exchange);
        }
    }
}
