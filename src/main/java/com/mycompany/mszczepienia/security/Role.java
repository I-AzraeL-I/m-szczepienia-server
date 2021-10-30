package com.mycompany.mszczepienia.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    USER("USER"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");

    public final String value;
}
