package org.example.controllers;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

@ServerEndpoint(value = "/chat")
public class WebSocket {
    private Session session;
    private static Set<WebSocket> webSockets;
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(
        Session session
    ) throws IOException, EncodeException {
        this.session = session;
        webSockets.add(this);

        System.out.println("Dupa");
        broadcast("Twoja matka");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(String message)
            throws IOException, EncodeException {

        webSockets.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}