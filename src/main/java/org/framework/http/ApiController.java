package org.framework.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.framework.http.request.Request;
import org.framework.http.response.Response;
import org.framework.http.response.ResponseBuilder;
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
        Response res;
        HttpStatus status;

        try {
            Route route = router.dispatch(exchange);

            if (route != null) {
                Method handler = route.getHandler(HttpMethod.valueOf(exchange.getRequestMethod()));

                if (handler != null) {
                    res = (Response) handler.invoke(this, req);
                }
                else {
                    ResponseBuilder responseBuilder = new ResponseBuilder();
                    res = responseBuilder
                            .setStatusCode(HttpStatus.NOT_ALLOWED_405)
                            .setBody(exchange.getRequestMethod() + " not allowed")
                            .build();
                }
            } else {
                ResponseBuilder responseBuilder = new ResponseBuilder();
                res = responseBuilder
                        .setStatusCode(HttpStatus.NOT_FOUND_404)
                        .setBody("Not found")
                        .build();
            }


        } catch (InvocationTargetException | IllegalAccessException | RuntimeException e) {
            System.out.println(e);
            ResponseBuilder responseBuilder = new ResponseBuilder();
            res = responseBuilder
                    .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR_500)
                    .setBody("Inernal server error")
                    .build();
        }

        HttpUtils.sendResponse(res, exchange);
    }
}
