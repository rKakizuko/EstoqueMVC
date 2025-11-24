package com.example.demo.service;

import com.example.demo.model.Categoria;
import com.example.demo.model.Fornecedor;
import com.example.demo.model.Produto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.FornecedorRepository;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Produto salvar(Produto produto) {
        Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Categoria inválida ou não encontrada."
                ));

        Fornecedor fornecedor = fornecedorRepository.findById(produto.getFornecedor().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Fornecedor inválido ou não encontrado."
                ));

        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto com ID " + id + " não encontrado."
                ));
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id).map(produtoExistente -> {

            Categoria categoria = categoriaRepository.findById(produtoAtualizado.getCategoria().getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Categoria inválida ou não encontrada."
                    ));

            Fornecedor fornecedor = fornecedorRepository.findById(produtoAtualizado.getFornecedor().getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Fornecedor inválido ou não encontrado."
                    ));

            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
            produtoExistente.setPreco(produtoAtualizado.getPreco());
            produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
            produtoExistente.setCategoria(categoria);
            produtoExistente.setFornecedor(fornecedor);

            return produtoRepository.save(produtoExistente);
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Produto com ID " + id + " não encontrado para atualização."
        ));
    }

    public Produto atualizarEstoque(Long id, Integer novoEstoque) {
        return produtoRepository.findById(id).map(produto -> {
            produto.setQuantidadeEstoque(novoEstoque);
            return produtoRepository.save(produto);
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Produto com ID " + id + " não encontrado para atualização de estoque."
        ));
    }

    public void deletar(Long id) {
        produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto com ID " + id + " não encontrado."
                ));

        produtoRepository.deleteById(id);
    }

    public List<Produto> buscarPorCategoriaId(Long categoriaId) {
        return produtoRepository.findByCategoriaId(categoriaId);
    }
}
