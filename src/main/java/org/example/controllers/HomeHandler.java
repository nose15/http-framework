package org.example.controllers;
import org.framework.ApiController;

public class HomeHandler extends ApiController {
    public String index(String request) {
        return "Hello world";
    }
}

