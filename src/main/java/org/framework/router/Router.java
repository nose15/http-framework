package org.framework.router;

import com.sun.net.httpserver.HttpExchange;
import org.framework.http.utils.HttpMethod;
import org.framework.router.annotations.HttpGET;
import org.framework.router.annotations.HttpMapping;
import org.framework.router.annotations.HttpPOST;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Router {
    private final Map<String, Route> routeMap = new HashMap<>();

    public Router(Method[] handler) {
        mapHandlers(handler);
    }

    private void mapHandlers(Method[] handlers) {
        for (Method handler : handlers) {
            mapSingleHandler(handler);
        }
    }

    private void mapSingleHandler(Method handler) {
        Annotation annotation = findFirstHttpMappingAnnotation(handler);

        if (annotation == null) return;

        try {
            String handlerUri = (String) annotation.annotationType().getMethod("value").invoke(annotation);
            handlerUri = parseMethodUri(handlerUri);

            HttpMethod httpMethod = (HttpMethod) annotation.annotationType().getField("httpMethod").get(annotation);

            Route route = routeMap.getOrDefault(handlerUri, new Route(handlerUri));
            route.addHandler(httpMethod, handler);

            routeMap.put(handlerUri, route);
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            throw new RuntimeException("Invalid Http Mapping annotation " + e.getMessage());
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
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

    private Annotation findFirstHttpMappingAnnotation(Method handler) {
        Annotation[] annotations = handler.getDeclaredAnnotations();

        for (Annotation annotation: annotations) {
            if (!annotation.annotationType().isAnnotationPresent(HttpMapping.class)) continue;

            return annotation;
        }

        return null;
    }

    private String parseMethodUri(String methodUri) {
        if (methodUri.startsWith("/")) methodUri = methodUri.substring(1);
        return methodUri;
    }
}
