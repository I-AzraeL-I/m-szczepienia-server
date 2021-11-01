package com.mycompany.mszczepienia.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER"),
    MODERATOR("ROLE_MODERATOR"),
    ADMIN("ROLE_ADMIN");

    public final String value;
}
