package com.oficina.api.dto;

import java.util.List;

import com.oficina.api.model.StatusOrdemServico;
import com.oficina.api.model.TipoCalculoPreco;

import jakarta.validation.constraints.NotNull;

public record OrdemServicoRequestDTO(
    @NotNull(message = "O cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "O veículo é obrigatório")
    Long veiculoId,

    StatusOrdemServico status,
    String descricaoProblema,
    String observacoes,
    TipoCalculoPreco tipoCalculoPreco,
    List<Long> servicoIds,
    List<Long> pecaIds
) {}
