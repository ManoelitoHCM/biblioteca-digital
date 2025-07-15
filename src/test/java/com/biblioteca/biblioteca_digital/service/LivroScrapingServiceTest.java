package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.dto.LivroScrapingDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.repository.AutorRepository;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import com.biblioteca.biblioteca_digital.service.impl.LivroScrapingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroScrapingServiceTest {

    @Mock private LivroRepository livroRepository;
    @Mock private AutorRepository autorRepository;
    @Mock private CategoriaRepository categoriaRepository;

    @InjectMocks private LivroScrapingServiceImpl scrapingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarLivroRaspadoComSucesso() {
        // Mock DTO extraído
        LivroScrapingDTO dto = new LivroScrapingDTO();
        dto.setTitulo("Livro Teste");
        dto.setAutor("Autor Teste");
        dto.setPreco(BigDecimal.TEN);
        dto.setIsbn("1234567890");
        dto.setCategoria("Romance");
        dto.setAnoPublicacao(2024);

        // Spy no service para simular extração
        LivroScrapingServiceImpl spyService = Mockito.spy(scrapingService);
        doReturn(dto).when(spyService).extrairDadosLivro(anyString());

        when(livroRepository.findByIsbn("1234567890")).thenReturn(Optional.empty());
        when(autorRepository.findByNomeIgnoreCase("Autor Teste")).thenReturn(Optional.empty());
        when(categoriaRepository.findByNomeIgnoreCase("Romance")).thenReturn(Optional.empty());

        Autor autorSalvo = new Autor();
        autorSalvo.setId(1L);
        autorSalvo.setNome("Autor Teste");

        Categoria categoriaSalva = new Categoria();
        categoriaSalva.setId(1L);
        categoriaSalva.setNome("Romance");

        when(autorRepository.save(any())).thenReturn(autorSalvo);
        when(categoriaRepository.save(any())).thenReturn(categoriaSalva);
        when(livroRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Livro salvo = spyService.salvarLivroRaspado("https://url-fake.com");

        assertNotNull(salvo);
        assertEquals("Livro Teste", salvo.getTitulo());
        assertEquals("1234567890", salvo.getIsbn());
        assertEquals("Autor Teste", salvo.getAutor().getNome());
        assertEquals("Romance", salvo.getCategoria().getNome());
    }
}
