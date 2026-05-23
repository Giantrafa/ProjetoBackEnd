package com.example.demo.dto;

import com.example.demo.model.Perfil;
import com.example.demo.model.UsuarioModel;

public record UsuarioResponseDTO(Long id, String nome, String email, Perfil perfil) {
    public static UsuarioResponseDTO from(UsuarioModel usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil()
        );
    }
}
