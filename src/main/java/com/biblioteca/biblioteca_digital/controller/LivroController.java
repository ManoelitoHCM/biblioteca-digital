package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.mapper.DtoMapper;
import com.biblioteca.biblioteca_digital.model.dto.LivroRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.service.AutorService;
import com.biblioteca.biblioteca_digital.service.CategoriaService;
import com.biblioteca.biblioteca_digital.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Livros", description = "Operações relacionadas aos livros")
@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;
    private final AutorService autorService;
    private final CategoriaService categoriaService;

    public LivroController(LivroService livroService, AutorService autorService, CategoriaService categoriaService) {
        this.livroService = livroService;
        this.autorService = autorService;
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Listar livros com filtros opcionais")
    @GetMapping
    public List<LivroResponseDTO> listarTodos(
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) Long autor) {

        List<Livro> livros;

        if (categoria != null) {
            livros = livroService.buscarPorCategoria(categoria);
        } else if (ano != null) {
            livros = livroService.buscarPorAno(ano);
        } else if (autor != null) {
            livros = livroService.buscarPorAutor(autor);
        } else {
            livros = livroService.listarTodos();
        }

        return livros.stream().map(DtoMapper::toLivroDTO).collect(Collectors.toList());
    }

    @Operation(summary = "Buscar livro por ID")
    @GetMapping("/{id}")
    public LivroResponseDTO buscarPorId(@PathVariable Long id) {
        return DtoMapper.toLivroDTO(livroService.buscarPorId(id));
    }

    @Operation(summary = "Buscar livros por título")
    @GetMapping("/search")
    public List<LivroResponseDTO> buscarPorTitulo(@RequestParam String titulo) {
        return livroService.buscarPorTitulo(titulo)
                .stream()
                .map(DtoMapper::toLivroDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Criar novo livro")
    @PostMapping
    public ResponseEntity<LivroResponseDTO> criar(@RequestBody @Valid LivroRequestDTO dto) {
        Autor autor = autorService.buscarPorId(dto.getAutorId());
        Categoria categoria = categoriaService.listarTodas().stream()
                .filter(c -> c.getId().equals(dto.getCategoriaId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setIsbn(dto.getIsbn());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setPreco(dto.getPreco());
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        Livro criado = livroService.criar(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toLivroDTO(criado));
    }

    @Operation(summary = "Atualizar livro existente")
    @PutMapping("/{id}")
    public LivroResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid LivroRequestDTO dto) {
        Autor autor = autorService.buscarPorId(dto.getAutorId());
        Categoria categoria = categoriaService.listarTodas().stream()
                .filter(c -> c.getId().equals(dto.getCategoriaId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setIsbn(dto.getIsbn());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setPreco(dto.getPreco());
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        return DtoMapper.toLivroDTO(livroService.atualizar(id, livro));
    }

    @Operation(summary = "Deletar livro")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

