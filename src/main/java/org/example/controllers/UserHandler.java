package org.example.controllers;

import org.framework.http.ApiController;
import org.framework.router.annotations.HttpGET;
import org.framework.router.annotations.HttpPOST;

import java.util.Set;

public class UserHandler extends ApiController {
    private final Set<String> users;

    public UserHandler(Set<String> users) {
        this.users = users;
    }

    @HttpGET("/")
    public String allUsers(String request) {
        return users.toString();
    }

    @HttpPOST("/add")
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