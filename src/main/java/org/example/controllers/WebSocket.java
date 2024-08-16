package org.example.controllers;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint(value = "/chat/{room_id}")
public class WebSocket {
    private Session session;
    private static final Set<WebSocket> connections = new HashSet<>();
    private int roomId;

    @OnOpen
    public void onOpen(
        Session session
    ) throws IOException, EncodeException {
        this.session = session;
        connections.add(this);
        Map<String, List<String>> params = session.getRequestParameterMap();
        this.roomId = Integer.parseInt(params.get("room_id").get(0));

        System.out.println("Connected " + session.getBasicRemote() + " to room " + this.roomId);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        sendToRoom(this.roomId, message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        connections.remove(this);
        System.out.println("Closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println(throwable.getMessage());
    }

    private static void sendToRoom(int roomId, String message) {
        connections.forEach(endpoint -> {
            try {
                if (endpoint.getRoomId() == roomId)
                    endpoint.session.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    private static void broadcast(String message)
            throws IOException, EncodeException {

        connections.forEach(endpoint -> {
            try {
                endpoint.session.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}