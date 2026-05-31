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
public interface ClientRepository extends JpaRepository<ClienteModel, Long> {

    Optional<ClientModel> findByCpfCnpj(String cpfCnpj); //o spring consegue gerar o SQL sozinho

    boolean existsByCpfCnpj(String cpfCnpj);

    @Query("SELECT COUNT(c) > 0 FROM ClientModel c WHERE c.cpfCnpj = :cpfCnpj AND c.id <> :id")
    boolean existsCpfCnpjEmOutroCliente(@Param("cpfCnpj") String cpfCnpj, @Param("id") Long id);

    @Query("""
        SELECT c FROM ClientModel c
        WHERE LOWER(c.nomeCompleto) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR c.cpfCnpj LIKE CONCAT('%', :termo, '%')
    """)
    Page<ClientModel> buscarPorNomeOuCpfCnpj(@Param("termo") String termo, Pageable pageable);
}