package com.example.demo.repository;

import com.example.demo.model.ClienteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    Optional<ClienteModel> findByCpfCnpj(String cpfCnpj); //o spring consegue gerar o SQL sozinho

    boolean existsByCpfCnpj(String cpfCnpj);

    @Query("SELECT COUNT(c) > 0 FROM ClienteModel c WHERE c.cpfCnpj = :cpfCnpj AND c.id <> :id")
    boolean existsCpfCnpjEmOutroCliente(@Param("cpfCnpj") String cpfCnpj, @Param("id") Long id);

    @Query("""
        SELECT c FROM ClienteModel c
        WHERE LOWER(c.nomeCompleto) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR c.cpfCnpj LIKE CONCAT('%', :termo, '%')
    """)
    Page<ClienteModel> buscarPorNomeOuCpfCnpj(@Param("termo") String termo, Pageable pageable);
}