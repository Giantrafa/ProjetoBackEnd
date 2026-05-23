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

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder,
        TokenService tokenService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        UsuarioModel usuario = usuarioRepository.findByEmail(request.email())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais invalidas."));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais invalidas.");
        }

        String token = tokenService.gerarToken(usuario);
        return new AuthResponseDTO(token, UsuarioResponseDTO.from(usuario));
    }
}
