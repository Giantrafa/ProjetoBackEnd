package com.example.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.UsuarioResponseDTO;
import com.example.demo.model.UsuarioModel;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthResponseDTO login(LoginRequestDTO request) {
        if (request == null || request.email() == null || request.senha() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e senha sao obrigatorios.");
        }

        String email = request.email().trim();
        if (email.isEmpty() || request.senha().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e senha sao obrigatorios.");
        }

        UsuarioModel usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais invalidas."));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais invalidas.");
        }

        String token = tokenService.gerarToken(usuario);
        return new AuthResponseDTO(token, UsuarioResponseDTO.from(usuario));
    }
}
