package com.oficina.api.service.strategy;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.oficina.api.model.PecaModel;
import com.oficina.api.model.ServicoModel;
import com.oficina.api.model.TipoCalculoPreco;

@Component
public class PrecoPadraoStrategy implements PrecoStrategy {
    @Override
    public TipoCalculoPreco getTipo() {
        return TipoCalculoPreco.PADRAO;
    }

    @Override
    public BigDecimal calcular(List<ServicoModel> servicos, List<PecaModel> pecas) {
        return PrecoStrategyUtils.totalBase(servicos, pecas);
    }
}
