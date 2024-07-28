package org.example.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.HttpEndpoint;
import org.example.HttpUtils;
import org.example.router.Router;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class UserHandler implements HttpHandler {
    private final Set<String> users;

    public UserHandler(Set<String> users) {
        this.users = users;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Router router = new Router(Arrays.asList(UserHandler.class.getDeclaredMethods()));

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
            res = "Internal server error";
            HttpUtils.sendResponse(500, res, exchange);
        }
    }

    @HttpEndpoint("add")
    private String addUser(String request) {
        String username = request;

        String res;
        if (users.contains(username)) {
            res = "Error";
        } else {
            users.add(username);
            res = "Added new user: " + username;
        }

        return res;
    }
}