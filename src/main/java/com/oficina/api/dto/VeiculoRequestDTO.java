package com.oficina.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeiculoRequestDTO(
    @NotBlank(message = "A placa é obrigatória")
    String placa,

    String marca,

    @NotBlank(message = "O modelo é obrigatório")
    String modelo,

    Integer ano,

    String historicoServicos,

    @NotNull(message = "O cliente é obrigatório")
    Long clienteId
) {}
