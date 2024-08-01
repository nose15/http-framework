package org.example.controllers;

import org.framework.ApiController;

import java.util.Set;

public class UserHandler extends ApiController {
    private final Set<String> users;

    public UserHandler(Set<String> users) {
        this.users = users;
    }

    public String allUsers(String request) {
        return users.toString();
    }

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