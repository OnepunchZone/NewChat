package ru.otus.chat.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private int port;
    private Map<String, ClientPart> clients;

    public Server(int port) {
        this.port = port;
        this.clients = new HashMap<>();
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Сервер запущен. Порт : " + port);
            while (true) {
                Socket socket = server.accept();
                subscribe(new ClientPart(this, socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void subscribe(ClientPart clientPart) {
        broadcastMessage("К чату присоеденился: " + clientPart.getUsername());
        clients.put(clientPart.getUsername(), clientPart);
    }

    public synchronized void unsubscribe(ClientPart clientPart) {
        clients.remove(clientPart.getUsername(), clientPart);
        broadcastMessage(clientPart.getUsername() + " покинул чат.");
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientPart c : clients.values()) {
            c.sendMessage(message);
        }
    }

    public synchronized void toUserMessage(String name, String message) {
        clients.get(name).sendMessage(message);
    }

    public synchronized void changeName(String pastName, String newName) {
        ClientPart newC = clients.remove(pastName);
        clients.put(newName, newC);
    }
}
