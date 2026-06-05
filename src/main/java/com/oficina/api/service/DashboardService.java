package com.oficina.api.service;

import org.springframework.stereotype.Service;

import com.oficina.api.dto.DashboardResumoDTO;
import com.oficina.api.model.StatusOrdemServico;
import com.oficina.api.repository.ClienteRepository;
import com.oficina.api.repository.OrdemServicoRepository;
import com.oficina.api.repository.PecaRepository;
import com.oficina.api.repository.ServicoRepository;
import com.oficina.api.repository.VeiculoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final ServicoRepository servicoRepository;
    private final PecaRepository pecaRepository;
    private final OrdemServicoRepository ordemServicoRepository;

    public DashboardResumoDTO resumo() {
        return new DashboardResumoDTO(
            clienteRepository.count(),
            veiculoRepository.count(),
            servicoRepository.count(),
            pecaRepository.count(),
            pecaRepository.contarEstoqueBaixo(),
            ordemServicoRepository.countByStatus(StatusOrdemServico.ABERTA),
            ordemServicoRepository.countByStatus(StatusOrdemServico.EM_ANDAMENTO),
            ordemServicoRepository.countByStatus(StatusOrdemServico.CONCLUIDA),
            ordemServicoRepository.countByStatus(StatusOrdemServico.CANCELADA)
        );
    }
}
