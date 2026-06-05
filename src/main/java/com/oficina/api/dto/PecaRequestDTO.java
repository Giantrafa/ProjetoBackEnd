package com.oficina.api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record PecaRequestDTO(
    @NotBlank(message = "O nome da peça é obrigatório")
    String nome,

    String descricao,

    @PositiveOrZero(message = "A quantidade não pode ser negativa")
    Integer quantidade,

    @PositiveOrZero(message = "O estoque mínimo não pode ser negativo")
    Integer estoqueMinimo,

    String fornecedor,

    @PositiveOrZero(message = "O preço unitário não pode ser negativo")
    BigDecimal precoUnitario
) {}
