package org.framework.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.framework.http.request.Request;
import org.framework.http.utils.HttpMethod;
import org.framework.http.utils.HttpStatus;
import org.framework.http.utils.HttpUtils;
import org.framework.router.Route;
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
        HttpStatus status;

        try {
            Route route = router.dispatch(exchange);

            if (route != null) {
                Method handler = route.getHandler(HttpMethod.valueOf(exchange.getRequestMethod()));

                if (handler != null) {
                    res = handler.invoke(this, req).toString();
                    status = HttpStatus.OK_200;
                }
                else {
                    res = exchange.getRequestMethod() + " not allowed";
                    status = HttpStatus.NOT_ALLOWED_405;
                }
            } else {
                res = "Not found";
                status = HttpStatus.NOT_FOUND_404;
            }


        } catch (InvocationTargetException | IllegalAccessException | RuntimeException e) {
            System.out.println(e);
            res = "Internal server error";
            status = HttpStatus.INTERNAL_SERVER_ERROR_500;
        }

        HttpUtils.sendResponse(status.getStatusCode(), res, exchange);
    }
}
