package org.example.controllers;
import org.example.HttpEndpoint;

public class HomeHandler extends ApiController {
    @HttpEndpoint("/")
    public String index(String request) {
        return "Hello world";
    }
}

