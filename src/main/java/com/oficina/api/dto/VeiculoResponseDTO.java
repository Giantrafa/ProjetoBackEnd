package com.oficina.api.dto;

import java.time.LocalDateTime;

import com.oficina.api.model.VeiculoModel;

public record VeiculoResponseDTO(
    Long id,
    String placa,
    String marca,
    String modelo,
    Integer ano,
    String historicoServicos,
    Long clienteId,
    String clienteNome,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
    public static VeiculoResponseDTO from(VeiculoModel model) {
        return new VeiculoResponseDTO(
            model.getId(),
            model.getPlaca(),
            model.getMarca(),
            model.getModelo(),
            model.getAno(),
            model.getHistoricoServicos(),
            model.getCliente().getId(),
            model.getCliente().getNomeCompleto(),
            model.getCriadoEm(),
            model.getAtualizadoEm()
        );
    }
}
