package com.mycompany.mszczepienia.service;

import com.mycompany.mszczepienia.dto.LoginFormDto;
import com.mycompany.mszczepienia.exception.InvalidCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    public String login(LoginFormDto loginFormDto) {
        if (loginFormDto.getLogin().equals(USERNAME) && loginFormDto.getPassword().equals(PASSWORD)) {
            return "Ok";
        } else {
            throw new InvalidCredentialsException("Given credentials are invalid");
        }
    }
}
