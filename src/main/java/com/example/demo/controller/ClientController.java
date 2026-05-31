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
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/allClientes")
    public Page<ClientResponseDTO> getAllClientes(
            @RequestParam(required = false) String busca,
            @PageableDefault(size = 10, sort = "nomeCompleto", direction = Sort.Direction.ASC) Pageable pageable) {
        return clientService.listar(busca, pageable);
    }

    @PostMapping("/add")
    public ClientResponseDTO addNewCliente(@RequestBody @Valid ClientRequestDTO cliente) {
        return clientService.criar(cliente);
    }

    @GetMapping("/{id}")
    public ClientResponseDTO getClient(@PathVariable Long id) {
        return clientService.buscarPorId(id);
    }

    @PutMapping("/update/{id}")
    public ClientResponseDTO updateCliente(@RequestBody @Valid ClientRequestDTO cliente, @PathVariable Long id) {
        return clientService.atualizar(id, cliente);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        boolean deletado = clientService.deletarPorId(id);
        if (deletado) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
