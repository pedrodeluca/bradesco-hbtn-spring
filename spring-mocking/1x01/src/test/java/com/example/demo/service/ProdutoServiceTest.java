package com.example.demo.service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveRetornarProdutoQuandoIdExistir() {
        Long id = 1L;
        Produto produto = new Produto(id, "Produto A", 100.0);
        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        Produto resultado = produtoService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals("Produto A", resultado.getNome());
        assertEquals(100.0, resultado.getPreco());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        Long id = 2L;
        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            produtoService.buscarPorId(id);
        });

        assertEquals("Produto não encontrado", excecao.getMessage());
    }
}