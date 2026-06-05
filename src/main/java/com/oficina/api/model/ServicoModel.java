package com.oficina.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicos")
@Getter
@Setter
@NoArgsConstructor
public class ServicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(name = "tempo_estimado_minutos")
    private Integer tempoEstimadoMinutos;

    @Column(name = "preco_base", nullable = false, precision = 12, scale = 2)
    private BigDecimal precoBase = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean ativo = true;

    @JsonIgnore
    @ManyToMany(mappedBy = "servicos")
    private List<OrdemServicoModel> ordensServico = new ArrayList<>();

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = criadoEm;
        if (ativo == null) ativo = true;
        if (precoBase == null) precoBase = BigDecimal.ZERO;
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();
        if (ativo == null) ativo = true;
        if (precoBase == null) precoBase = BigDecimal.ZERO;
    }
}
