package ru.otus.chat.server;


public interface AuthenticationProvider {
    void initialize();
    String getAdminName();
    boolean authenticate(ClientPart clientPart, String login, String password);
}
