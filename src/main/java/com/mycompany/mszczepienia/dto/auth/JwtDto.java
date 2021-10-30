package com.mycompany.mszczepienia.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class JwtDto {

    private final String type = "Bearer";

    private String accessToken;
    private String refreshToken;
    private Long id;
    private String email;
    private List<String> roles;
}
