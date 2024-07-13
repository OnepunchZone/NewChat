package ru.otus.chat.server;


import ru.otus.chat.server.authbd.User;

import java.sql.SQLException;
import java.util.List;

public interface AuthenticationProvider extends AutoCloseable{
    String getAdminName();

    List<User> getAll();

    boolean authenticate(ClientPart clientPart, String login, String password);

    void createUser(int id, String login, String username, String password) throws SQLException;

    void createRole(int id, UserRole role) throws SQLException;

    void addRoleToUSer(int userId, int roleId) throws SQLException;
}
