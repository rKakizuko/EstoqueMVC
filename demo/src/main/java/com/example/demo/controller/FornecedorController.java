package com.example.demo.controller;


import com.example.demo.model.Fornecedor;
import com.example.demo.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<Fornecedor> criarFornecedor(@RequestBody Fornecedor fornecedor) {
        Fornecedor novoFornecedor = fornecedorService.salvar(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFornecedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedor) {
        Fornecedor fornecedorAtualizado = fornecedorService.atualizar(id, fornecedor);
        return ResponseEntity.ok(fornecedorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFornecedor(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}