package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDTO {

    @NotBlank(message = "O nome completo é obrigatório")
    @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
    private String nomeCompleto;

    @NotBlank(message = "O CPF ou CNPJ é obrigatório")
    private String cpfCnpj;

    @Pattern(
        regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$",
        message = "Telefone inválido. Use o formato: (99) 99999-9999"
    )
    private String telefone;

    @Email(message = "Formato de e-mail inválido")
    private String email;

    private String endereco;
}
