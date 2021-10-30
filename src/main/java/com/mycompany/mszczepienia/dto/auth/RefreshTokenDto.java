package com.mycompany.mszczepienia.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshTokenDto {

    private final String type = "Bearer";

    private String accessToken;
    private String refreshToken;
}
