package org.framework.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.framework.http.request.Request;
import org.framework.router.Router;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ApiController implements HttpHandler {
    private final Router router;

    public ApiController() {
        this.router = new Router(this.getClass().getDeclaredMethods());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Request req = HttpUtils.getRequest(exchange);
        String res;

        try {
            Method method = router.dispatch(exchange);
            res = method.invoke(this, req).toString();
            HttpUtils.sendResponse(200, res, exchange);
        } catch (NoSuchMethodException e) {
            res = "Not found";
            HttpUtils.sendResponse(404, res, exchange);
        } catch (InvocationTargetException | IllegalAccessException | RuntimeException e) {
            System.out.println(e);
            res = "Internal server error";
            HttpUtils.sendResponse(500, res, exchange);
        }
    }
}
