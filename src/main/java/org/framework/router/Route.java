package org.framework.router;

import java.lang.reflect.Method;
import java.util.*;

public class Route {
    private final String subUri;
    private final Map<HttpMethod, Method> methodHandlers = new HashMap<>();

    public Route(String subUri) {
        this.subUri = subUri;
    }

    public String getUri() {
        return subUri;
    }

    public Method getHandler(HttpMethod method) {
        if (!methodHandlers.containsKey(method)) {
            return null;
        }

        return methodHandlers.get(method);
    }

    public void addHandler(HttpMethod method, Method handler) {
        methodHandlers.put(method, handler);
    }

    public Set<HttpMethod> getAllowedMethods() {
        return methodHandlers.keySet();
    }
}
