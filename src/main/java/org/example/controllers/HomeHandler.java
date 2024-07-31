package org.example.controllers;
import org.framework.HttpEndpoint;
import org.framework.ApiController;

public class HomeHandler extends ApiController {
    @HttpEndpoint("/")
    public String index(String request) {
        return "Hello world";
    }
}

