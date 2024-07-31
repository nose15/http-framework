package org.example.controllers;

import org.example.HttpEndpoint;
import java.util.Set;

public class UserHandler extends ApiController {
    private final Set<String> users;

    public UserHandler(Set<String> users) {
        this.users = users;
    }

    @HttpEndpoint("/")
    public String allUsers(String request) {
        return users.toString();
    }

    @HttpEndpoint("/add")
    public String addUser(String request) {
        String username = request;

        String res;
        if (users.contains(username)) {
            res = "User already exists";
        } else {
            users.add(username);
            res = "Added new user: " + username;
        }

        return res;
    }
}