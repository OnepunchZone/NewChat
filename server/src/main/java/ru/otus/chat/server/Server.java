package ru.otus.chat.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ClientPart> clients;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
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
        clients.add(clientPart);
    }

    public synchronized void unsubscribe(ClientPart clientPart) {
        clients.remove(clientPart);
        broadcastMessage(clientPart.getUsername() + " покинул чат.");
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientPart c : clients) {
            c.sendMessage(message);
        }
    }

    public synchronized void toUserMessage(String name, String message) {
        for (ClientPart c : clients) {
            if (c.getUsername().equals(name)) {
                c.sendMessage(message);
            }
        }
    }
}
