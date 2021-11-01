package com.mycompany.mszczepienia.dto.auth;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterRequestDto {

    @Email
    @Size(min = 3, max = 100, message = "Email length must be between {min} and {max}")
    private String email;

    @Size(min = 8, max = 100, message = "Password length must be between {min} and {max}")
    private String password;

    @Size(min = 1, max = 100, message = "First name length must be between {min} and {max}")
    private String firstName;

    @Size(min = 1, max = 100, message = "Last name length must be between {min} and {max}")
    private String lastName;

    @PESEL(message = "Invalid pesel format")
    private String pesel;
}
