package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        Usuario usuario = new Usuario(1L, "João", "joao@email.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.buscarPorId(2L));
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        Usuario usuarioParaSalvar = new Usuario(null, "Maria", "maria@email.com");
        Usuario usuarioSalvo = new Usuario(3L, "Maria", "maria@email.com");
        when(usuarioRepository.save(usuarioParaSalvar)).thenReturn(usuarioSalvo);

        Usuario resultado = usuarioService.salvarUsuario(usuarioParaSalvar);

        assertNotNull(resultado.getId());
        assertEquals("Maria", resultado.getNome());
        assertEquals("maria@email.com", resultado.getEmail());
    }
}
