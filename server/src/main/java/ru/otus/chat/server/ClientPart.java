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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ClientPart(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
                System.out.println("Подключился новый клиент");
                while (true) {
                    String message = in.readUTF();
                    if (message.equals("/exit")) {
                        sendMessage("/disconnect");
                        break;
                    }
                    if (message.startsWith("/auth ")) {
                        String[] str = message.split(" ");
                        if (str.length != 3) {
                            sendMessage("Неверный формат команды /auth");
                            continue;
                        }
                        if (server.getAuthenticationProvider().authenticate(this, str[1], str[2])) {
                            break;
                        }
                        continue;
                    }
                    sendMessage("Сначала нужно пройти аутентификацию '/auth login password'");
                }
                while (true) {
                    String message = in.readUTF();
                    kickUser(message);
                    if (message.startsWith("/")) {
                        if (message.equals("/exit")) {
                            sendMessage("/disconnect");
                            break;
                        } else if (message.startsWith("/name ")) {
                            if (nameTake(message) == null) {
                                this.sendMessage("Введите новое имя через пробел после команды /name :");
                            } else {
                                server.broadcastMessage(username + " сменил имя на " + nameTake(message));
                                server.changeName(username, nameTake(message));
                                username = nameTake(message);
                            }
                        } else if (message.startsWith("/w ")) {
                            if (nameTake(message) == null) {
                                this.sendMessage("Введите имя получателя через пробел после команды /w :");
                            } else {
                                server.toUserMessage(nameTake(message), "Личное сообщение от " +
                                        username + ": " + message.substring(nameTake(message).length() + 4));
                            }
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

    public void kickUser(String message) {
        if (message.startsWith("/kick ")) {
            server.banUser(this, nameTake(message));
        }
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
        if (part.length < 2) {
            return null;
        }
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
