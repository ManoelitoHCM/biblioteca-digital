package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.mapper.DtoMapper;
import com.biblioteca.biblioteca_digital.model.dto.LivroRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Livros", description = "Operações relacionadas aos livros")
@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @Operation(summary = "Listar livros com filtros opcionais")
    @GetMapping
    public List<LivroResponseDTO> listarTodos(
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) Long autor) {

        if (categoria != null) {
            return livroService.buscarPorCategoria(categoria);
        } else if (ano != null) {
            return livroService.buscarPorAno(ano);
        } else if (autor != null) {
            return livroService.buscarPorAutor(autor);
        } else {
            return livroService.listarTodos();
        }
    }

    @Operation(summary = "Buscar livro por ID")
    @GetMapping("/{id}")
    public LivroResponseDTO buscarPorId(@PathVariable Long id) {
        return DtoMapper.toLivroDTO(livroService.buscarPorId(id));
    }

    @Operation(summary = "Buscar livros por título")
    @GetMapping("/search")
    public List<LivroResponseDTO> buscarPorTitulo(@RequestParam String titulo) {
        return livroService.buscarPorTitulo(titulo);
    }

    @Operation(summary = "Criar novo livro")
    @PostMapping
    public ResponseEntity<LivroResponseDTO> criar(@RequestBody @Valid LivroRequestDTO dto) {
        LivroResponseDTO criado = livroService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(summary = "Atualizar livro existente")
    @PutMapping("/{id}")
    public LivroResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid LivroRequestDTO dto) {
        return livroService.atualizar(id, dto);
    }

    @Operation(summary = "Deletar livro")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
