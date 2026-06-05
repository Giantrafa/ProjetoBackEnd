package com.oficina.api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ServicoRequestDTO(
    @NotBlank(message = "O nome do serviço é obrigatório")
    String nome,

    String descricao,

    @PositiveOrZero(message = "O tempo estimado não pode ser negativo")
    Integer tempoEstimadoMinutos,

    @NotNull(message = "O preço-base é obrigatório")
    @PositiveOrZero(message = "O preço-base não pode ser negativo")
    BigDecimal precoBase,

    Boolean ativo
) {}
