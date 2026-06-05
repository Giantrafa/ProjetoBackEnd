package com.oficina.api.service.strategy;

import java.math.BigDecimal;
import java.util.List;

import com.oficina.api.model.PecaModel;
import com.oficina.api.model.ServicoModel;
import com.oficina.api.model.TipoCalculoPreco;

public interface PrecoStrategy {
    TipoCalculoPreco getTipo();
    BigDecimal calcular(List<ServicoModel> servicos, List<PecaModel> pecas);
}
