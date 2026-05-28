package com.medevent.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.medevent.api.dto.AuthResponseDTO;
import com.medevent.api.dto.LoginRequestDTO;
import com.medevent.api.dto.RegisterRequestDTO;
import com.medevent.api.dto.UsuarioResponseDTO;
import com.medevent.api.model.Perfil;
import com.medevent.api.model.UsuarioModel;
import com.medevent.api.repository.UsuarioRepository;
import com.medevent.api.security.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthResponseDTO login(LoginRequestDTO request) {
        if (request == null || request.email() == null || request.password() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e senha sao obrigatorios.");
        }

        String email = request.email().trim();
        if (email.isEmpty() || request.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e senha sao obrigatorios.");
        }

        UsuarioModel usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais invalidas."));

        if (!passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais invalidas.");
        }

        String token = tokenService.gerarToken(usuario);
        return new AuthResponseDTO(token, UsuarioResponseDTO.from(usuario));
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (request == null || request.name() == null || request.email() == null || request.password() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome, email e senha sao obrigatorios.");
        }

        String email = request.email().trim();
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ja cadastrado.");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setName(request.name().trim());
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRole(Perfil.CLIENTE);

        usuarioRepository.save(usuario);

        String token = tokenService.gerarToken(usuario);
        return new AuthResponseDTO(token, UsuarioResponseDTO.from(usuario));
    }

    public UsuarioResponseDTO me(String email) {
        UsuarioModel usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao encontrado."));
        return UsuarioResponseDTO.from(usuario);
    }
}
