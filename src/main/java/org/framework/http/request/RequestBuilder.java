package org.framework.http.request;

import com.sun.net.httpserver.Headers;
import org.framework.Builder;
import org.framework.http.utils.HttpMethod;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RequestBuilder implements Builder<Request> {
    private HttpMethod method;
    private URI uri;
    private final Headers headers = new Headers();
    private String body;

    public RequestBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public RequestBuilder addHeader(String key, String value) {
        List<String> parsedValue = new ArrayList<>();
        parsedValue.add(value);
        this.headers.put(key, parsedValue);
        return this;
    }

    public RequestBuilder addHeaders(Headers headers) {
        this.headers.putAll(headers);
        return this;
    }

    public RequestBuilder setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public RequestBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public Request build() {
        return new Request(
            this.method,
            this.headers,
            this.uri,
            this.body
        );
    }
}
