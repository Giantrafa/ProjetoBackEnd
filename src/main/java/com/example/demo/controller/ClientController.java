package com.example.demo.controller;

import com.example.demo.dto.ClienteRequestDTO;
import com.example.demo.dto.ClienteResponseDTO;
import com.example.demo.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/allClientes")
    public Page<ClienteResponseDTO> getAllClientes(
            @RequestParam(required = false) String busca,
            @PageableDefault(size = 10, sort = "nomeCompleto", direction = Sort.Direction.ASC) Pageable pageable) {
        return clienteService.listar(busca, pageable);
    }

    @PostMapping("/add")
    public ClienteResponseDTO addNewCliente(@RequestBody @Valid ClienteRequestDTO cliente) {
        return clienteService.criar(cliente);
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO getCliente(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @PutMapping("/update/{id}")
    public ClienteResponseDTO updateCliente(@RequestBody @Valid ClienteRequestDTO cliente, @PathVariable Long id) {
        return clienteService.atualizar(id, cliente);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        boolean deletado = clienteService.deletarPorId(id);
        if (deletado) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
