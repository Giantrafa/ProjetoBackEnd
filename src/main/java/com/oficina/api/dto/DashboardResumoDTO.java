package com.oficina.api.dto;

public record DashboardResumoDTO(
    long clientes,
    long veiculos,
    long servicos,
    long pecas,
    long pecasEstoqueBaixo,
    long ordensAbertas,
    long ordensEmAndamento,
    long ordensConcluidas,
    long ordensCanceladas
) {}
