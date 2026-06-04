package com.oficina.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.oficina.api.dto.AtualizarUsuarioRequestDTO;
import com.oficina.api.dto.UsuarioResponseDTO;
import com.oficina.api.model.Perfil;
import com.oficina.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll()
            .stream()
            .map(UsuarioResponseDTO::from)
            .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        var usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado."));
        return UsuarioResponseDTO.from(usuario);
    }

    public UsuarioResponseDTO atualizar(Long id, AtualizarUsuarioRequestDTO request) {
        var usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado."));

        if (request.name() != null && !request.name().isBlank()) {
            usuario.setName(request.name().trim());
        }

        if (request.role() != null && !request.role().isBlank()) {
            try {
                usuario.setRole(Perfil.valueOf(request.role().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Perfil invalido: " + request.role());
            }
        }

        usuarioRepository.save(usuario);
        return UsuarioResponseDTO.from(usuario);
    }

    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
