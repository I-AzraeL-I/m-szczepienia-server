package com.mycompany.mszczepienia.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequestDto {

    @Email
    private String email;

    @NotBlank
    private String password;
}
