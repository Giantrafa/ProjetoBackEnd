package com.oficina.api.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Component;

import com.oficina.api.model.PecaModel;
import com.oficina.api.model.ServicoModel;
import com.oficina.api.model.TipoCalculoPreco;

@Component
public class PrecoUrgenciaStrategy implements PrecoStrategy {
    @Override
    public TipoCalculoPreco getTipo() {
        return TipoCalculoPreco.URGENCIA_20;
    }

    @Override
    public BigDecimal calcular(List<ServicoModel> servicos, List<PecaModel> pecas) {
        return PrecoStrategyUtils.totalBase(servicos, pecas)
            .multiply(new BigDecimal("1.20"))
            .setScale(2, RoundingMode.HALF_UP);
    }
}
