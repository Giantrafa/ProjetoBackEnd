package com.oficina.api.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Component;

import com.oficina.api.model.PecaModel;
import com.oficina.api.model.ServicoModel;
import com.oficina.api.model.TipoCalculoPreco;

@Component
public class PrecoDescontoStrategy implements PrecoStrategy {
    @Override
    public TipoCalculoPreco getTipo() {
        return TipoCalculoPreco.DESCONTO_10;
    }

    @Override
    public BigDecimal calcular(List<ServicoModel> servicos, List<PecaModel> pecas) {
        return PrecoStrategyUtils.totalBase(servicos, pecas)
            .multiply(new BigDecimal("0.90"))
            .setScale(2, RoundingMode.HALF_UP);
    }
}
