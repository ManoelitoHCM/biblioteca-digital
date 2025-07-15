package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.dto.CategoriaRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.CategoriaResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import com.biblioteca.biblioteca_digital.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarCategoriaComSucesso() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO();
        dto.setNome("Ficção");
        dto.setDescricao("Livros fictícios");

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaResponseDTO response = categoriaService.criar(dto);

        assertNotNull(response);
        assertEquals("Ficção", response.getNome());
        assertEquals("Livros fictícios", response.getDescricao());
    }

    @Test
    void deveListarLivrosDaCategoria() {
        Autor autor = new Autor();
        autor.setId(1L);

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("1984");
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        when(livroRepository.findByCategoriaId(1L)).thenReturn(Collections.singletonList(livro));

        var livros = categoriaService.listarLivrosDaCategoria(1L);

        assertEquals(1, livros.size());
        assertEquals("1984", livros.get(0).getTitulo());
    }


    @Test
    void deveBuscarCategoriaPorIdComSucesso() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Terror");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        Categoria encontrada = categoriaService.buscarPorId(1L);

        assertNotNull(encontrada);
        assertEquals("Terror", encontrada.getNome());
    }

    @Test
    void deveLancarExcecaoSeCategoriaNaoEncontrada() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            categoriaService.buscarPorId(99L);
        });

        assertEquals("Categoria não encontrada com ID: 99", ex.getMessage());
    }
}
