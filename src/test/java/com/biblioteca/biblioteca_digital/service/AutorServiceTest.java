package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.dto.AutorRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.AutorResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.repository.AutorRepository;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import com.biblioteca.biblioteca_digital.service.impl.AutorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private AutorServiceImpl autorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarAutorComSucesso() {
        AutorRequestDTO dto = new AutorRequestDTO();
        dto.setNome("Clarice Lispector");
        dto.setEmail("clarice@literatura.com");
        dto.setDataNascimento(LocalDate.of(1920, 12, 10));

        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome(dto.getNome());
        autor.setEmail(dto.getEmail());
        autor.setDataNascimento(dto.getDataNascimento());

        when(autorRepository.save(any(Autor.class))).thenReturn(autor);

        AutorResponseDTO response = autorService.criar(dto);

        assertNotNull(response);
        assertEquals("Clarice Lispector", response.getNome());
        assertEquals("clarice@literatura.com", response.getEmail());
    }

    @Test
    void deveLancarExcecaoSeAutorNaoEncontrado() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            autorService.buscarPorId(99L);
        });

        assertEquals("Autor não encontrado com ID: 99", ex.getMessage());
    }

    @Test
    void deveAtualizarAutorComSucesso() {
        Autor existente = new Autor();
        existente.setId(1L);

        AutorRequestDTO dto = new AutorRequestDTO();
        dto.setNome("Atualizado");
        dto.setEmail("atualizado@email.com");
        dto.setDataNascimento(LocalDate.of(1950, 1, 1));

        when(autorRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(autorRepository.save(any(Autor.class))).thenReturn(existente);

        AutorResponseDTO atualizado = autorService.atualizar(1L, dto);

        assertNotNull(atualizado);
        assertEquals("Atualizado", atualizado.getNome());
    }

    @Test
    void deveListarLivrosDoAutor() {
        Autor autor = new Autor();
        autor.setId(1L);

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("A Hora da Estrela");
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        when(livroRepository.findByAutorId(1L)).thenReturn(Collections.singletonList(livro));

        var livros = autorService.listarLivrosDoAutor(1L);

        assertEquals(1, livros.size());
        assertEquals("A Hora da Estrela", livros.get(0).getTitulo());
    }

}
