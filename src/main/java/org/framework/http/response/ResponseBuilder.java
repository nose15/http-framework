package org.framework.http.response;

import com.sun.net.httpserver.Headers;
import org.framework.Builder;
import org.framework.http.utils.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ResponseBuilder implements Builder<Response> {
    final private Headers headers = new Headers();
    private HttpStatus statusCode;
    private String body;

    public ResponseBuilder addHeader(String key, String value) {
        List<String> parsedValue = new ArrayList<>();
        parsedValue.add(value);
        this.headers.put(key, parsedValue);
        return this;
    }

    public ResponseBuilder addHeaders(Headers headers) {
        this.headers.putAll(headers);
        return this;
    }

    public ResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public ResponseBuilder setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public Response build() {
        return new Response(statusCode, headers, body);
    }
}
