package org.example.controllers;
import org.framework.http.ApiController;
import org.framework.router.annotations.HttpGET;

public class HomeHandler extends ApiController {
    @HttpGET("/")
    public String index(String request) {
        return "Hello world";
    }
}

