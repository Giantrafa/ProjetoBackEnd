package com.medevent.api.dto;

public record AuthResponseDTO(String token, UsuarioResponseDTO user) {
}
