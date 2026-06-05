package com.oficina.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
    @NotBlank(message = "O nome completo é obrigatório")
    @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
    String nomeCompleto,

    @NotBlank(message = "O CPF ou CNPJ é obrigatório")
    String cpfCnpj,

    String telefone,

    @Email(message = "E-mail inválido")
    String email,

    String endereco
) {}
