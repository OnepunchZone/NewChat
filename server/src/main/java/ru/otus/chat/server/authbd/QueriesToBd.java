package ru.otus.chat.server.authbd;

public class QueriesToBd {
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/chat_db";
    public static final String ADMIN_NAME_QUERY = "SELECT u.username FROM users u, roles r WHERE r.name = 'Админ';";
    public static final String LOGIN_USERNAME_PASSWORD_QUERY = "SELECT login, username, password FROM users;";
    public static final String USERS_QUERY = "SELECT * FROM users";
    public static final String CREATE_USER_QUERY = "insert into users (id, login, username, password) " +
            "values(?, ?, ?, ?);";
    public static final String CREATE_ROLE_QUERY = "insert into roles (id, name) values(?, ?);";
    public static final String ADD_ROLE_TO_USER_QUERY = "insert into users_to_roles (role_id, user_id) values(?, ?);";
    public static final String USER_ROLES_QUERY = """
            select r.id, r.name from roles r
            join users_to_roles ur ON r.id = ur.role_id
            WHERE user_id = ?
            ORDER BY id;
            """;


}
