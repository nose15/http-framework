package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.controllers.HomeHandler;
import org.example.controllers.UserHandler;
import org.example.controllers.WebSocket;
import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class Main {
    static Set<String> users = new HashSet<>();

    public static void main(String[] args) throws IOException, DeploymentException {
        int port = 8000;

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        Server server = new Server("localhost", 8080, "/chat", WebSocket.class);
        server.start();

        HomeHandler homeHandler = new HomeHandler();
        UserHandler userHandler = new UserHandler(users);

        httpServer.createContext("/", homeHandler);
        httpServer.createContext("/users", userHandler);

        httpServer.start();

        System.out.println("Siemens na porcie " + port);
    }
}