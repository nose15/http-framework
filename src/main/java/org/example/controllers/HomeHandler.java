package org.example.controllers;
import org.framework.http.ApiController;
import org.framework.http.request.Request;
import org.framework.http.response.Response;
import org.framework.http.response.ResponseBuilder;
import org.framework.http.utils.HttpStatus;
import org.framework.router.annotations.HttpGET;

public class HomeHandler extends ApiController {
    @HttpGET("/")
    public Response index(Request request) {
        ResponseBuilder responseBuilder = new ResponseBuilder();

        return responseBuilder
                .setStatusCode(HttpStatus.OK_200)
                .setBody("Hello world")
                .build();
    }
}

