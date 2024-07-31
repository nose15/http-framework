package org.example.router;

import com.sun.net.httpserver.HttpHandler;
import org.example.HttpEndpoint;
import org.example.HttpUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;

public class Router {
    private final Map<String, Method> methods = new HashMap<>();

    public Router(Method[] methods) {

        mapMethods(methods);
    }

    private void mapMethods(Method[] methods) {
        System.out.println("Mapping methods");

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

        System.out.println(this.methods.keySet());
    }

    public Method dispatch(URI uri) throws IllegalArgumentException {
        System.out.println("Uri:" + uri + ";");

        String subUri = HttpUtils.parseUri(uri, 1);
        System.out.println("SubUri:" + subUri + ";");
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
        if (methods.containsKey(subUri)) return methods.get(subUri);

        throw new NoSuchMethodException();
    }
}
