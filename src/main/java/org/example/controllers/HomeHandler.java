package org.example.controllers;
import org.framework.http.ApiController;
import org.framework.http.request.Request;
import org.framework.router.annotations.HttpGET;

public class HomeHandler extends ApiController {
    @HttpGET("/")
    public String index(Request request) {
        return "Hello world";
    }
}

