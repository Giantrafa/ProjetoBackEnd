package com.oficina.api.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oficina.api.dto.PecaRequestDTO;
import com.oficina.api.dto.PecaResponseDTO;
import com.oficina.api.exception.RecursoNaoEncontradoException;
import com.oficina.api.model.PecaModel;
import com.oficina.api.repository.PecaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PecaService {
    private final PecaRepository pecaRepository;

    @Transactional
    public PecaResponseDTO criar(PecaRequestDTO request) {
        PecaModel peca = new PecaModel();
        preencher(peca, request);
        return PecaResponseDTO.from(pecaRepository.save(peca));
    }

    @Transactional(readOnly = true)
    public Page<PecaResponseDTO> listar(String busca, Pageable pageable) {
        return pecaRepository.buscar(busca == null ? null : busca.trim(), pageable).map(PecaResponseDTO::from);
    }

    @Transactional(readOnly = true)
    public PecaResponseDTO buscarPorId(Long id) {
        return PecaResponseDTO.from(buscarModelPorId(id));
    }

    @Transactional
    public PecaResponseDTO atualizar(Long id, PecaRequestDTO request) {
        PecaModel peca = buscarModelPorId(id);
        preencher(peca, request);
        return PecaResponseDTO.from(pecaRepository.save(peca));
    }

    @Transactional
    public void excluir(Long id) {
        if (!pecaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Peça não encontrada com id: " + id);
        }
        pecaRepository.deleteById(id);
    }

    public PecaModel buscarModelPorId(Long id) {
        return pecaRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Peça não encontrada com id: " + id));
    }

    private void preencher(PecaModel peca, PecaRequestDTO request) {
        peca.setNome(request.nome().trim());
        peca.setDescricao(limpar(request.descricao()));
        peca.setQuantidade(request.quantidade() == null ? 0 : request.quantidade());
        peca.setEstoqueMinimo(request.estoqueMinimo() == null ? 0 : request.estoqueMinimo());
        peca.setFornecedor(limpar(request.fornecedor()));
        peca.setPrecoUnitario(request.precoUnitario() == null ? BigDecimal.ZERO : request.precoUnitario());
    }

    private String limpar(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }
}
