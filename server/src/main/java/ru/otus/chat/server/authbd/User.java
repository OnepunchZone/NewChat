package ru.otus.chat.server.authbd;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String login;
    private String username;
    private String password;

    private List<Role> roles = new ArrayList<>();

    public User(int id, String login, String username, String password) {
        this.id = id;
        this.login = login;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id =" + id +
                ", login ='" + login + '\'' +
                ", username ='" + username + '\'' +
                ", password ='" + password + '\'' +
                ", role =" + roles +
                '}' + "\n";
    }
}
