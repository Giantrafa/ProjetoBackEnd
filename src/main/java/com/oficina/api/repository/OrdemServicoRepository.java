package com.oficina.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oficina.api.model.OrdemServicoModel;
import com.oficina.api.model.StatusOrdemServico;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServicoModel, Long> {
    long countByStatus(StatusOrdemServico status);

    @Query("""
        SELECT o FROM OrdemServicoModel o
        WHERE (:status IS NULL OR o.status = :status)
          AND (:termo IS NULL OR :termo = ''
               OR LOWER(o.cliente.nomeCompleto) LIKE LOWER(CONCAT('%', :termo, '%'))
               OR LOWER(o.veiculo.placa) LIKE LOWER(CONCAT('%', :termo, '%'))
               OR LOWER(o.veiculo.modelo) LIKE LOWER(CONCAT('%', :termo, '%'))
               OR LOWER(o.descricaoProblema) LIKE LOWER(CONCAT('%', :termo, '%')))
    """)
    Page<OrdemServicoModel> buscar(@Param("termo") String termo, @Param("status") StatusOrdemServico status, Pageable pageable);
}
