package com.example.demo.service;


import com.example.demo.model.Fornecedor;
import com.example.demo.repository.FornecedorRepository;

import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Fornecedor salvar(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor atualizar(Long id, Fornecedor fornecedorAtualizado) {
        return fornecedorRepository.findById(id).map(fornecedorExistente -> {
            fornecedorExistente.setNome(fornecedorAtualizado.getNome());
            fornecedorExistente.setContato(fornecedorAtualizado.getContato());
            return fornecedorRepository.save(fornecedorExistente);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado para atualização."));
    }

    public void deletar(Long id) {
        if (!produtoRepository.findAll().stream().filter(p -> p.getFornecedor().getId().equals(id)).toList().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível deletar. Existem produtos associados a este fornecedor.");
        }

        fornecedorRepository.deleteById(id);
    }
}
