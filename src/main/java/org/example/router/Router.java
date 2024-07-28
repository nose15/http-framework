package org.example.router;

import com.sun.net.httpserver.HttpHandler;
import org.example.HttpEndpoint;
import org.example.HttpUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Router {
    private final List<Method> methods;

    public Router(List<Method> methods) {
        this.methods = methods;
    }

    public Method dispatch(URI uri) throws IllegalArgumentException {
        String subUri = HttpUtils.parseUri(uri, 1);
        Method method;

        try {
            method = findMethod(subUri);
            return method;
        } catch (NoSuchMethodException e) {
            System.out.println("Dispatcher didn't find the method with specified name - " + subUri);
            throw new IllegalArgumentException("No such method");
        }
    }

    private Method findMethod(String subUri) throws NoSuchMethodException {
        for (Method method : methods) {
            if (method.isAnnotationPresent(HttpEndpoint.class)) {
                HttpEndpoint httpEndpoint = method.getAnnotation(HttpEndpoint.class);
                if (httpEndpoint.value().equals(subUri)) {
                    return method;
                }
            }
        }

        throw new NoSuchMethodException();
    }
}
