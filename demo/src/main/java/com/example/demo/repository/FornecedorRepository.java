package com.example.demo.repository;

import com.example.demo.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    // Verificar duplicidade
    Optional<Fornecedor> findByContato(String contato);
    Optional<Fornecedor> findByCnpj(String cnpj);

}
