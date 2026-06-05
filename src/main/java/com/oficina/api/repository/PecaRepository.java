package com.oficina.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oficina.api.model.PecaModel;

@Repository
public interface PecaRepository extends JpaRepository<PecaModel, Long> {
    @Query("""
        SELECT p FROM PecaModel p
        WHERE :termo IS NULL OR :termo = ''
           OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.fornecedor) LIKE LOWER(CONCAT('%', :termo, '%'))
    """)
    Page<PecaModel> buscar(@Param("termo") String termo, Pageable pageable);

    @Query("SELECT COUNT(p) FROM PecaModel p WHERE p.quantidade <= p.estoqueMinimo")
    long contarEstoqueBaixo();
}
