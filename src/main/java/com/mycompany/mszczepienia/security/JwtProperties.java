package com.mycompany.mszczepienia.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JwtProperties {

    TOKEN_CLAIM_AUTHORITIES("TOKEN_CLAIM_AUTHORITIES"),
    TOKEN_PREFIX_ROLE("ROLE_");

    public final String value;
}
