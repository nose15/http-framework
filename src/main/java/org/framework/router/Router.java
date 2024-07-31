package org.framework.router;

import com.sun.net.httpserver.HttpExchange;
import org.framework.HttpEndpoint;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Router {
    private final Map<String, Method> methods = new HashMap<>();

    public Router(Method[] methods) {
        mapMethods(methods);
    }

    private void mapMethods(Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(HttpEndpoint.class)) {
                HttpEndpoint httpEndpoint = method.getAnnotation(HttpEndpoint.class);
                String path = httpEndpoint.value();

                if (path.startsWith("/")) {
                    path = path.substring(1);
                }

                this.methods.put(path, method);
            }
        }
    }

    public Method dispatch(HttpExchange exchange) throws NoSuchMethodException {
        try {
            URI requestUri = exchange.getRequestURI();
            URI path = new URI(exchange.getHttpContext().getPath());

            String functionName = path.relativize(requestUri).getPath();
            return methods.get(functionName);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Method findMethod(String subUri) {
        if (methods.containsKey(subUri)) return methods.get(subUri);

        return null;
    }
}
