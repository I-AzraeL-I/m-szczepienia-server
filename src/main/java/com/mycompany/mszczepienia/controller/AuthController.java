package com.mycompany.mszczepienia.controller;

import com.mycompany.mszczepienia.dto.LoginFormDto;
import com.mycompany.mszczepienia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginFormDto loginFormDto) {
        return ResponseEntity.ok(authService.login(loginFormDto));
    }
}
