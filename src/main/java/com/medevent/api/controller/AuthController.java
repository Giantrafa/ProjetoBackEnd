package com.medevent.api.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medevent.api.dto.AuthResponseDTO;
import com.medevent.api.dto.LoginRequestDTO;
import com.medevent.api.dto.RegisterRequestDTO;
import com.medevent.api.dto.UsuarioResponseDTO;
import com.medevent.api.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @GetMapping("/me")
    public UsuarioResponseDTO me(Principal principal) {
        return authService.me(principal.getName());
    }
}
