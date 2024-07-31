package org.example.controllers;
import org.example.HttpEndpoint;

public class HomeHandler extends ApiController {
    public HomeHandler() {
        System.out.println("Homehandler");
    }

    @HttpEndpoint("/")
    public String index(String request) {
        return "Hello world";
    }
}

