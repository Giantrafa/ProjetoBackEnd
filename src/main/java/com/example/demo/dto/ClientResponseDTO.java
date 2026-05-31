package com.example.demo.dto;

import com.example.demo.model.VeiculoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ClientResponseDTO {

    private Long id;
    private String nomeCompleto;
    private String cpfCnpj;
    private String telefone;
    private String email;
    private String endereco;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private List<VeiculoModel> veiculos;
}
