package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.mapper.DtoMapper;
import com.biblioteca.biblioteca_digital.model.dto.CategoriaRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.CategoriaResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Categorias", description = "Operações relacionadas às categorias de livros")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Listar todas as categorias")
    @GetMapping
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaService.listarTodas()
                .stream()
                .map(DtoMapper::toCategoriaDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Criar uma nova categoria")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@RequestBody @Valid CategoriaRequestDTO dto) {
        Categoria nova = new Categoria();
        nova.setNome(dto.getNome());
        nova.setDescricao(dto.getDescricao());
        Categoria criada = categoriaService.criar(nova);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toCategoriaDTO(criada));
    }

    @Operation(summary = "Listar livros de uma categoria")
    @GetMapping("/{id}/livros")
    public List<LivroResponseDTO> listarLivrosDaCategoria(@PathVariable Long id) {
        return categoriaService.listarLivrosDaCategoria(id)
                .stream()
                .map(DtoMapper::toLivroDTO)
                .collect(Collectors.toList());
    }
}
