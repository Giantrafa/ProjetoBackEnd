package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", nullable = false, length = 150)
    @NotBlank(message = "O nome completo é obrigatório")
    @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
    private String nomeCompleto;

    @Column(name = "cpf_cnpj", nullable = false, unique = true, length = 18)
    @NotBlank(message = "O CPF ou CNPJ é obrigatório")
    private String cpfCnpj;

    @Column(name = "telefone", length = 20)
    @Pattern(
        regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$",
        message = "Telefone inválido. Use o formato: (99) 99999-9999"
    )
    private String telefone;

    @Column(name = "email", length = 100)
    @Email(message = "Formato de e-mail inválido")
    private String email;

    @Column(name = "endereco", length = 250)
    private String endereco;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    // Relacionamento com Veiculo - 3
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private List<VeiculoModel> veiculos;

    @PrePersist
    public void aoSalvar() {
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void aoAtualizar() {
        this.atualizadoEm = LocalDateTime.now();
    }
}