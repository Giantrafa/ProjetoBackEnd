package com.oficina.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oficina.api.dto.OrdemServicoRequestDTO;
import com.oficina.api.dto.OrdemServicoResponseDTO;
import com.oficina.api.model.StatusOrdemServico;
import com.oficina.api.service.OrdemServicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ordens-servico")
@RequiredArgsConstructor
public class OrdemServicoController {
    private final OrdemServicoService ordemServicoService;

    @GetMapping
    public Page<OrdemServicoResponseDTO> listar(
        @RequestParam(required = false) String busca,
        @RequestParam(required = false) StatusOrdemServico status,
        @PageableDefault(size = 10, sort = "criadoEm", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ordemServicoService.listar(busca, status, pageable);
    }

    @GetMapping("/{id}")
    public OrdemServicoResponseDTO buscarPorId(@PathVariable Long id) {
        return ordemServicoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoResponseDTO criar(@RequestBody @Valid OrdemServicoRequestDTO request) {
        return ordemServicoService.criar(request);
    }

    @PutMapping("/{id}")
    public OrdemServicoResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid OrdemServicoRequestDTO request) {
        return ordemServicoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        ordemServicoService.excluir(id);
    }
}
