package com.oficina.api.service.strategy;

import java.math.BigDecimal;
import java.util.List;

import com.oficina.api.model.PecaModel;
import com.oficina.api.model.ServicoModel;

class PrecoStrategyUtils {
    private PrecoStrategyUtils() {}

    static BigDecimal totalBase(List<ServicoModel> servicos, List<PecaModel> pecas) {
        BigDecimal totalServicos = servicos.stream()
            .map(ServicoModel::getPrecoBase)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPecas = pecas.stream()
            .map(PecaModel::getPrecoUnitario)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalServicos.add(totalPecas);
    }
}
