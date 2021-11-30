package com.mycompany.mszczepienia.security;

public enum Role {

    USER,
    MODERATOR,
    ADMIN;

    public String withPrefix() {
        return "ROLE_" + name();
    }
}
