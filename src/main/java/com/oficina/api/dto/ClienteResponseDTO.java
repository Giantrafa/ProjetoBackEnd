package com.oficina.api.dto;

import java.time.LocalDateTime;

import com.oficina.api.model.ClienteModel;

public record ClienteResponseDTO(
    Long id,
    String nomeCompleto,
    String cpfCnpj,
    String telefone,
    String email,
    String endereco,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
    public static ClienteResponseDTO from(ClienteModel model) {
        return new ClienteResponseDTO(
            model.getId(),
            model.getNomeCompleto(),
            model.getCpfCnpj(),
            model.getTelefone(),
            model.getEmail(),
            model.getEndereco(),
            model.getCriadoEm(),
            model.getAtualizadoEm()
        );
    }
}
