package com.oficina.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.oficina.api.model.OrdemServicoModel;
import com.oficina.api.model.PecaModel;
import com.oficina.api.model.ServicoModel;
import com.oficina.api.model.StatusOrdemServico;
import com.oficina.api.model.TipoCalculoPreco;

public record OrdemServicoResponseDTO(
    Long id,
    Long clienteId,
    String clienteNome,
    Long veiculoId,
    String veiculoResumo,
    StatusOrdemServico status,
    String descricaoProblema,
    String observacoes,
    TipoCalculoPreco tipoCalculoPreco,
    BigDecimal valorTotal,
    List<Long> servicoIds,
    List<String> servicos,
    List<Long> pecaIds,
    List<String> pecas,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
    public static OrdemServicoResponseDTO from(OrdemServicoModel model) {
        return new OrdemServicoResponseDTO(
            model.getId(),
            model.getCliente().getId(),
            model.getCliente().getNomeCompleto(),
            model.getVeiculo().getId(),
            model.getVeiculo().getPlaca() + " - " + model.getVeiculo().getModelo(),
            model.getStatus(),
            model.getDescricaoProblema(),
            model.getObservacoes(),
            model.getTipoCalculoPreco(),
            model.getValorTotal(),
            model.getServicos().stream().map(ServicoModel::getId).toList(),
            model.getServicos().stream().map(ServicoModel::getNome).toList(),
            model.getPecas().stream().map(PecaModel::getId).toList(),
            model.getPecas().stream().map(PecaModel::getNome).toList(),
            model.getCriadoEm(),
            model.getAtualizadoEm()
        );
    }
}
