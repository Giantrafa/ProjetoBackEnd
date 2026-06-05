package com.oficina.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oficina.api.model.ServicoModel;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoModel, Long> {
    @Query("""
        SELECT s FROM ServicoModel s
        WHERE :termo IS NULL OR :termo = ''
           OR LOWER(s.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(s.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))
    """)
    Page<ServicoModel> buscar(@Param("termo") String termo, Pageable pageable);
}
