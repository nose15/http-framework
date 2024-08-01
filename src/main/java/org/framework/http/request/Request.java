package org.framework.http.request;

import com.sun.net.httpserver.Headers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Request {
    final private String method;
    final private Headers headers;
    final private URI uri;
    final private String body;

    public Request(
        String method,
        Headers headers,
        URI uri,
        String body
    ) {
        this.method = method;
        this.headers = headers;
        this.uri = uri;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public Headers getHeaders() {
        return headers;
    }
}
