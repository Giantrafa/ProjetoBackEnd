package com.oficina.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.oficina.api.model.PecaModel;

public record PecaResponseDTO(
    Long id,
    String nome,
    String descricao,
    Integer quantidade,
    Integer estoqueMinimo,
    String fornecedor,
    BigDecimal precoUnitario,
    Boolean estoqueBaixo,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
    public static PecaResponseDTO from(PecaModel model) {
        return new PecaResponseDTO(
            model.getId(),
            model.getNome(),
            model.getDescricao(),
            model.getQuantidade(),
            model.getEstoqueMinimo(),
            model.getFornecedor(),
            model.getPrecoUnitario(),
            model.getQuantidade() <= model.getEstoqueMinimo(),
            model.getCriadoEm(),
            model.getAtualizadoEm()
        );
    }
}
