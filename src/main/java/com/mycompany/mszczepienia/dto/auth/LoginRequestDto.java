package com.mycompany.mszczepienia.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequestDto {

    @Email
    @Size(min = 3, max = 100, message = "Email length must be between {min} and {max}")
    private String email;

    @Size(min = 8, max = 100, message = "Password length must be between {min} and {max}")
    private String password;
}
