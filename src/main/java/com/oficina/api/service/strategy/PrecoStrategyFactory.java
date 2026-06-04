package com.oficina.api.service.strategy;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.oficina.api.model.PecaModel;
import com.oficina.api.model.ServicoModel;
import com.oficina.api.model.TipoCalculoPreco;

@Component
public class PrecoStrategyFactory {
    private final Map<TipoCalculoPreco, PrecoStrategy> strategies = new EnumMap<>(TipoCalculoPreco.class);

    public PrecoStrategyFactory(List<PrecoStrategy> strategiesDisponiveis) {
        strategiesDisponiveis.forEach(strategy -> strategies.put(strategy.getTipo(), strategy));
    }

    public BigDecimal calcular(TipoCalculoPreco tipo, List<ServicoModel> servicos, List<PecaModel> pecas) {
        TipoCalculoPreco tipoSeguro = tipo == null ? TipoCalculoPreco.PADRAO : tipo;
        PrecoStrategy strategy = strategies.getOrDefault(tipoSeguro, strategies.get(TipoCalculoPreco.PADRAO));
        return strategy.calcular(servicos, pecas);
    }
}
