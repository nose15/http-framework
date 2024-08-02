package org.framework.router;

import com.sun.net.httpserver.HttpExchange;
import org.framework.http.utils.HttpMethod;
import org.framework.router.annotations.HttpGET;
import org.framework.router.annotations.HttpPOST;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Router {
    private final Map<String, Route> routeMap = new HashMap<>();

    public Router(Method[] methods) {
        mapMethods(methods);
    }

    private void mapMethods(Method[] methods) {
        for (Method method : methods) {
            mapSingleMethod(method);
        }
    }

    private void mapSingleMethod(Method method) {
        String methodUri = "";

        if (method.isAnnotationPresent(HttpPOST.class)) {
            methodUri = parseMethodUri(method.getAnnotation(HttpPOST.class).value());

            Route route = routeMap.getOrDefault(methodUri, new Route(methodUri));
            route.addHandler(HttpMethod.POST, method);

            routeMap.put(methodUri, route);
        }
        else if (method.isAnnotationPresent(HttpGET.class)) {
            methodUri = parseMethodUri(method.getAnnotation(HttpGET.class).value());

            Route route = routeMap.getOrDefault(methodUri, new Route(methodUri));
            route.addHandler(HttpMethod.GET, method);

            routeMap.put(methodUri, route);
        }
    }

    public Route dispatch(HttpExchange exchange) {
        try {
            URI requestUri = exchange.getRequestURI();
            URI path = new URI(exchange.getHttpContext().getPath());
            String functionName = path.relativize(requestUri).getPath();

            return routeMap.get(functionName);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String parseMethodUri(String methodUri) {
        if (methodUri.startsWith("/")) methodUri = methodUri.substring(1);
        return methodUri;
    }
}
