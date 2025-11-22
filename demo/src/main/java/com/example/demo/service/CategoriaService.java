package com.example.demo.service;



import com.example.demo.model.Categoria;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deletar(Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada.");
        }

        if (!produtoRepository.findByCategoriaId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível deletar. Existem produtos associados a esta categoria.");
        }

        categoriaRepository.deleteById(id);
    }
}
