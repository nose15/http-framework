package org.framework.router;

import com.sun.net.httpserver.HttpExchange;
import org.framework.router.annotations.HttpGET;
import org.framework.router.annotations.HttpPOST;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Router {
    private final Map<String, Route> routeMap = new HashMap<>();
    private final Map<String, Method> getMethods = new HashMap<>();
    private final Map<String, Method> postMethods = new HashMap<>();

    public Router(Method[] methods) {
        mapMethods(methods);
    }

    private void mapMethods(Method[] methods) {
        for (Method method : methods) {
            mapSingleMethod(method);
        }
    }

    private void mapSingleMethod(Method method) {
        Map<String, Method> methodMap = null;
        String methodUri = "";

        if (method.isAnnotationPresent(HttpPOST.class)) {
            methodUri = method.getAnnotation(HttpPOST.class).value();

            Route route = routeMap.getOrDefault(methodUri, new Route(methodUri));
            route.addHandler(HttpMethod.POST, method);

            routeMap.put(methodUri, route);
            methodMap = this.postMethods;
        }
        else if (method.isAnnotationPresent(HttpGET.class)) {
            methodUri = method.getAnnotation(HttpGET.class).value();

            Route route = routeMap.getOrDefault(methodUri, new Route(methodUri));
            route.addHandler(HttpMethod.GET, method);

            routeMap.put(methodUri, route);
            methodMap = this.getMethods;
        } else {
            return;
        }

        if (methodUri.startsWith("/")) methodUri = methodUri.substring(1);

        methodMap.put(methodUri, method);
    }

    public Method dispatch(HttpExchange exchange) throws NoSuchMethodException {
        try {
            URI requestUri = exchange.getRequestURI();
            URI path = new URI(exchange.getHttpContext().getPath());

            String requestMethod = exchange.getRequestMethod();
            String functionName = path.relativize(requestUri).getPath();

            Map<String, Method> methods = switch (requestMethod) {
                case "POST" -> this.postMethods;
                case "GET" -> this.getMethods;
                default -> this.getMethods;
            };

            Method func = methods.get(functionName);
            if (func == null) throw new NoSuchMethodException();
            return func;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            throw new NoSuchMethodException();
        }
    }
}
