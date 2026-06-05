package com.oficina.api.dto;

import com.oficina.api.model.UsuarioModel;

public record UsuarioResponseDTO(String name, String email, String role) {
    public static UsuarioResponseDTO from(UsuarioModel usuario) {
        return new UsuarioResponseDTO(
            usuario.getName(),
            usuario.getEmail(),
            usuario.getRole().name()
        );
    }
}
