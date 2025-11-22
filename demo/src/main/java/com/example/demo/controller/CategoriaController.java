package com.example.demo.controller;

import com.example.demo.model.Categoria;
import com.example.demo.model.Produto;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
        Categoria novaCategoria = categoriaService.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/produtos")
    public List<Produto> listarProdutosPorCategoria(@PathVariable Long id) {
        return produtoService.buscarPorCategoriaId(id);
    }
}
