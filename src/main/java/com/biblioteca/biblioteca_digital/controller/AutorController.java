package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.model.dto.AutorRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.AutorResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Autores", description = "Operações relacionadas aos autores")
@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @Operation(summary = "Listar todos os autores")
    @GetMapping
    public List<AutorResponseDTO> listarTodos() {
        return autorService.listarTodos();
    }

    @Operation(summary = "Buscar autor por ID")
    @GetMapping("/{id}")
    public AutorResponseDTO buscarPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id);
    }

    @Operation(summary = "Criar novo autor")
    @PostMapping
    public ResponseEntity<AutorResponseDTO> criar(@RequestBody @Valid AutorRequestDTO dto) {
        AutorResponseDTO criado = autorService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(summary = "Atualizar autor existente")
    @PutMapping("/{id}")
    public AutorResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid AutorRequestDTO dto) {
        return autorService.atualizar(id, dto);
    }

    @Operation(summary = "Deletar autor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        autorService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar livros de um autor")
    @GetMapping("/{id}/livros")
    public List<LivroResponseDTO> listarLivrosDoAutor(@PathVariable Long id) {
        return autorService.listarLivrosDoAutor(id);
    }
}