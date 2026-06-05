package com.oficina.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oficina.api.model.ClienteModel;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    Optional<ClienteModel> findByCpfCnpj(String cpfCnpj);
    boolean existsByCpfCnpj(String cpfCnpj);

    @Query("SELECT COUNT(c) > 0 FROM ClienteModel c WHERE c.cpfCnpj = :cpfCnpj AND c.id <> :id")
    boolean existsCpfCnpjEmOutroCliente(@Param("cpfCnpj") String cpfCnpj, @Param("id") Long id);

    @Query("""
        SELECT c FROM ClienteModel c
        WHERE :termo IS NULL OR :termo = ''
           OR LOWER(c.nomeCompleto) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR c.cpfCnpj LIKE CONCAT('%', :termo, '%')
           OR LOWER(c.email) LIKE LOWER(CONCAT('%', :termo, '%'))
    """)
    Page<ClienteModel> buscar(@Param("termo") String termo, Pageable pageable);
}
