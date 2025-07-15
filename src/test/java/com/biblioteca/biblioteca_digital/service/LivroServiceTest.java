package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.dto.LivroRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.repository.AutorRepository;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import com.biblioteca.biblioteca_digital.service.impl.LivroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private LivroServiceImpl livroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAtualizarLivroComSucesso() {
        Livro existente = new Livro();
        existente.setId(1L);

        Autor autor = new Autor();
        autor.setId(1L);

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        LivroRequestDTO dto = new LivroRequestDTO();
        dto.setTitulo("Título Atualizado");
        dto.setIsbn("9876543210123");
        dto.setAnoPublicacao(2023);
        dto.setPreco(BigDecimal.valueOf(59.90));
        dto.setAutorId(1L);
        dto.setCategoriaId(1L);

        when(livroRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(livroRepository.save(any(Livro.class))).thenReturn(existente);

        LivroResponseDTO atualizado = livroService.atualizar(1L, dto);

        assertNotNull(atualizado);
        assertEquals("Título Atualizado", atualizado.getTitulo());
    }

    @Test
    void deveLancarExcecaoSeAutorNaoExiste() {
        LivroRequestDTO dto = new LivroRequestDTO();
        dto.setAutorId(99L);
        dto.setCategoriaId(1L);

        when(livroRepository.findById(1L)).thenReturn(Optional.of(new Livro()));
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            livroService.atualizar(1L, dto);
        });

        assertEquals("Autor não encontrado com ID: 99", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeCategoriaNaoExiste() {
        LivroRequestDTO dto = new LivroRequestDTO();
        dto.setAutorId(1L);
        dto.setCategoriaId(99L);

        when(livroRepository.findById(1L)).thenReturn(Optional.of(new Livro()));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(new Autor()));
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            livroService.atualizar(1L, dto);
        });

        assertEquals("Categoria não encontrada com ID: 99", ex.getMessage());
    }
}
