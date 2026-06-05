package com.oficina.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.oficina.api.model.ServicoModel;

public record ServicoResponseDTO(
    Long id,
    String nome,
    String descricao,
    Integer tempoEstimadoMinutos,
    BigDecimal precoBase,
    Boolean ativo,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
    public static ServicoResponseDTO from(ServicoModel model) {
        return new ServicoResponseDTO(
            model.getId(),
            model.getNome(),
            model.getDescricao(),
            model.getTempoEstimadoMinutos(),
            model.getPrecoBase(),
            model.getAtivo(),
            model.getCriadoEm(),
            model.getAtualizadoEm()
        );
    }
}
