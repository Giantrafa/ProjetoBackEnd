package com.oficina.api.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oficina.api.dto.AuthResponseDTO;
import com.oficina.api.dto.ForgotPasswordRequestDTO;
import com.oficina.api.dto.LoginRequestDTO;
import com.oficina.api.dto.RegisterRequestDTO;
import com.oficina.api.dto.ResetPasswordRequestDTO;
import com.oficina.api.dto.UsuarioResponseDTO;
import com.oficina.api.service.AuthService;
import com.oficina.api.service.PasswordResetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @GetMapping("/me")
    public UsuarioResponseDTO me(Principal principal) {
        return authService.me(principal.getName());
    }

    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
        passwordResetService.forgotPassword(request);
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        passwordResetService.resetPassword(request);
    }
}
