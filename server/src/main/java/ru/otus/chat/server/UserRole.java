package ru.otus.chat.server;

public enum UserRole {
    ADMIN("Админ"),
    USER("Юзер");

    String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
