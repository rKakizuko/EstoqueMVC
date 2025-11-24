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


        fornecedorRepository.findByContato(fornecedor.getContato()).ifPresent(f ->
        { throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Já existe um fornecedor com esse contato: " + fornecedor.getContato());
        });


        fornecedorRepository.findByCnpj(fornecedor.getCnpj()).ifPresent(f ->
        { throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Já existe um fornecedor com esse CNPJ: " + fornecedor.getCnpj());
        });

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor atualizar(Long id, Fornecedor fornecedorAtualizado) {

        Fornecedor fornecedorExistente = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Fornecedor com ID " + id + " não encontrado."
                ));


        fornecedorRepository.findByContato(fornecedorAtualizado.getContato()).ifPresent(f -> {
            if (!f.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Contato já usado por outro fornecedor.");
            }
        });


        fornecedorRepository.findByCnpj(fornecedorAtualizado.getCnpj()).ifPresent(f -> {
            if (!f.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "CNPJ já usado por outro fornecedor.");
            }
        });

        fornecedorExistente.setNome(fornecedorAtualizado.getNome());
        fornecedorExistente.setContato(fornecedorAtualizado.getContato());
        fornecedorExistente.setCnpj(fornecedorAtualizado.getCnpj());

        return fornecedorRepository.save(fornecedorExistente);
    }


    public void deletar(Long id) {

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fornecedor com ID " + id + " não encontrado."
                ));

        boolean temProdutos = produtoRepository.findAll().stream()
                .anyMatch(p -> p.getFornecedor().getId().equals(id));

        if (temProdutos) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível deletar o fornecedor, pois existem produtos vinculados."
            );
        }

        fornecedorRepository.delete(fornecedor);
    }
}
