package org.framework.http.response;

import com.sun.net.httpserver.Headers;
import org.framework.http.utils.HttpMethod;
import org.framework.http.utils.HttpStatus;

import javax.print.DocFlavor;
import java.net.URI;

public class Response {
    HttpStatus status;
    final private Headers headers = new Headers();
    private String body;

    public String getBody() {
        return body;
    }

    public Headers getHeaders() {
        return headers;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getStatusCode() {
        return status.getStatusCode();
    }

    public Response(HttpStatus status, Headers headers, String body) {
        this.status = status;
        this.headers.putAll(headers);
        this.body = body;
    }

    public Response(HttpStatus status, String body) {
        this.status = status;
        this.body = body;
    }
}
