/*
package ru.otus.chat.server;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAuthenticationProvider implements AuthenticationProvider{
    */
/*private class User {
        private int id;
        private String login;
        private String password;
        private String username;

        public User(int id, String login, String password, String username) {
            this.id = id;
            this.login = login;
            this.password = password;
            this.username = username;
        }
    }

    private Server server;
    private Map<User, String> users;

    public InMemoryAuthenticationProvider(Server server) {
        this.server = server;
        this.users = new HashMap<>();
        User admin = new User(1, "admin", "pass1", "user1");
        User user2 = new User(2, "log2", "pass2", "user2");
        User user3 = new User(3, "log3", "pass3", "user3");
        this.users.put(admin, UserRole.ADMIN.getValue());
        this.users.put(user2, UserRole.USER.getValue());
        this.users.put(user3, UserRole.USER.getValue());
    }

    public InMemoryAuthenticationProvider(Map<User, String> users) {
        this.users = users;
    }

    @Override
    public String getAdminName() {
        for (Map.Entry<User, String> entry : users.entrySet()) {
            if (entry.getValue().equals("Админ")) {
                return entry.getKey().username;
            }
        }
        return null;
    }

    @Override
    public void initialize() {
        System.out.println("Сервис аутентификации запущен.");
    }

    private String getUsernameByLogAndPass(String login, String password) {
        for (User u : users.keySet()) {
            if (u.login.equals(login) && u.password.equals(password)) {
                return u.username;
            }
        }
        return null;
    }

    @Override
    public synchronized boolean authenticate(ClientPart clientPart, String login, String password) {
        String authName = getUsernameByLogAndPass(login, password);
        if (authName == null) {
            clientPart.sendMessage("Некорректный логин/пароль");
            return false;
        }
        if (server.isUsernameIsBusy(authName)) {
            clientPart.sendMessage("Данное имя занято.");
            return false;
        }
        clientPart.setUsername(authName);
        server.subscribe(clientPart);
        clientPart.sendMessage("/correctauth " + authName);
        return true;
    }*//*


}
*/
