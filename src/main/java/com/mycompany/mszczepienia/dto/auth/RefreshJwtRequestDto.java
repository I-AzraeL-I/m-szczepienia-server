package com.mycompany.mszczepienia.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshJwtRequestDto {

    @NotBlank
    private String refreshToken;
}
