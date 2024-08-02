package org.example.controllers;

import org.framework.http.ApiController;
import org.framework.http.request.Request;
import org.framework.http.response.Response;
import org.framework.http.response.ResponseBuilder;
import org.framework.http.utils.HttpStatus;
import org.framework.router.annotations.HttpGET;
import org.framework.router.annotations.HttpPOST;

import java.util.Set;

public class UserHandler extends ApiController {
    private final Set<String> users;

    public UserHandler(Set<String> users) {
        this.users = users;
    }

    @HttpGET("/")
    public Response allUsers(Request request) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        return responseBuilder
                .setStatusCode(HttpStatus.OK_200)
                .setBody(users.toString())
                .build();
    }

    @HttpPOST("/add")
    public Response addUser(Request request) {
        String username = request.getBody();

        HttpStatus status;
        String body;
        if (users.contains(username)) {
            body = "User already exists";
            status = HttpStatus.CONFLICT_409;

            return new Response(status, body);
        }

        users.add(username);
        body = "Added new user: " + username;
        status = HttpStatus.CREATED_201;

        return new Response(status, body);
    }
}