package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.mapper.DtoMapper;
import com.biblioteca.biblioteca_digital.model.dto.AutorRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.AutorResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        return autorService.listarTodos()
                .stream()
                .map(DtoMapper::toAutorDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar autor por ID")
    @GetMapping("/{id}")
    public AutorResponseDTO buscarPorId(@PathVariable Long id) {
        return DtoMapper.toAutorDTO(autorService.buscarPorId(id));
    }

    @Operation(summary = "Criar novo autor")
    @PostMapping
    public ResponseEntity<AutorResponseDTO> criar(@RequestBody @Valid AutorRequestDTO dto) {
        Autor novo = new Autor();
        novo.setNome(dto.getNome());
        novo.setEmail(dto.getEmail());
        novo.setDataNascimento(dto.getDataNascimento());
        Autor criado = autorService.criar(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toAutorDTO(criado));
    }

    @Operation(summary = "Atualizar autor existente")
    @PutMapping("/{id}")
    public AutorResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid AutorRequestDTO dto) {
        Autor autor = new Autor();
        autor.setNome(dto.getNome());
        autor.setEmail(dto.getEmail());
        autor.setDataNascimento(dto.getDataNascimento());
        return DtoMapper.toAutorDTO(autorService.atualizar(id, autor));
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
        return autorService.listarLivrosDoAutor(id)
                .stream()
                .map(DtoMapper::toLivroDTO)
                .collect(Collectors.toList());
    }
}

