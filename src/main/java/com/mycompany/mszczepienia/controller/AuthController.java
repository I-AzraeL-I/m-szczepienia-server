package com.mycompany.mszczepienia.controller;

import com.mycompany.mszczepienia.dto.auth.*;
import com.mycompany.mszczepienia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        authService.register(registerRequestDto);
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshJwtResponseDto> refreshToken(@Valid @RequestBody RefreshJwtRequestDto refreshJwtRequestDto) {
        return ResponseEntity.ok(authService.refreshToken(refreshJwtRequestDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto){
        authService.updatePassword(updatePasswordDto);
        return ResponseEntity.ok("Ok");
    }
}
