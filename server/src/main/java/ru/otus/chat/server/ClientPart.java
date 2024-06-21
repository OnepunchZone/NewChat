package ru.otus.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientPart {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;
    private static int userCount = 0;

    public String getUsername() {
        return username;
    }


    public ClientPart(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        userCount ++;
        this.username = "User" + userCount;

        new Thread(() -> {
            try {
                System.out.println("Подключился новый клиент");
                while (true) {
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        if(message.equals("/exit")) {
                            sendMessage("/disconnect");
                            break;
                        } else if (message.startsWith("/name")) {
                            server.broadcastMessage(username + " сменил имя на " + nameTake(message));
                            username = nameTake(message);
                            continue;
                        } else if (message.startsWith("/w")) {
                            server.toUserMessage(nameTake(message), "Личное сообщение от " +
                                    username + ": " + message.substring(nameTake(message).length() + 4));
                            continue;
                        }

                        continue;
                    }
                    server.broadcastMessage(username + ": " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String nameTake(String str) {
        String[] part = str.split(" ");
        return part[1];
    }

    public void disconnect() {
        server.unsubscribe(this);

        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
