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

import com.oficina.api.dto.PecaRequestDTO;
import com.oficina.api.dto.PecaResponseDTO;
import com.oficina.api.service.PecaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pecas")
@RequiredArgsConstructor
public class PecaController {
    private final PecaService pecaService;

    @GetMapping
    public Page<PecaResponseDTO> listar(
        @RequestParam(required = false) String busca,
        @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return pecaService.listar(busca, pageable);
    }

    @GetMapping("/{id}")
    public PecaResponseDTO buscarPorId(@PathVariable Long id) {
        return pecaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PecaResponseDTO criar(@RequestBody @Valid PecaRequestDTO request) {
        return pecaService.criar(request);
    }

    @PutMapping("/{id}")
    public PecaResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid PecaRequestDTO request) {
        return pecaService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        pecaService.excluir(id);
    }
}
