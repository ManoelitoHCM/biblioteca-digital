package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.mapper.DtoMapper;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroScrapingDTO;
import com.biblioteca.biblioteca_digital.model.dto.UrlRequestDTO;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.service.LivroScrapingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Scraping", description = "Extração de dados de livros a partir de páginas externas")
@RestController
@RequestMapping("/api/scraping")
public class ScrapingController {

    private final LivroScrapingService scrapingService;
    private final CategoriaRepository categoriaRepository;

    public ScrapingController(LivroScrapingService scrapingService, CategoriaRepository categoriaRepository) {
        this.scrapingService = scrapingService;
        this.categoriaRepository = categoriaRepository;
    }

    @Operation(
            summary = "Extrair informações de um livro a partir de uma URL",
            description = "Retorna os dados extraídos da página, como título, autor, preço, ISBN, categoria e ano de publicação.",
            requestBody = @RequestBody(description = "Objeto contendo a URL da página do livro",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UrlRequestDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados extraídos com sucesso",
                            content = @Content(schema = @Schema(implementation = LivroScrapingDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro ao extrair dados")
            }
    )
    @PostMapping("/livros")
    public ResponseEntity<LivroScrapingDTO> extrair(@org.springframework.web.bind.annotation.RequestBody UrlRequestDTO request) {
        LivroScrapingDTO dto = scrapingService.extrairDadosLivro(request.getUrl());

        if (dto.getCategoria() != null) {
            Optional<Categoria> existente = categoriaRepository.findByNomeIgnoreCase(dto.getCategoria());
            existente.ifPresent(c -> dto.setCategoria(c.getNome()));
        }

        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Extrair e salvar livro a partir de uma URL",
            description = "Salva no sistema o livro extraído da página, evitando duplicidade por ISBN.",
            requestBody = @RequestBody(description = "Objeto contendo a URL da página do livro",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UrlRequestDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Livro salvo com sucesso",
                            content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro ao salvar livro")
            }
    )
    @PostMapping("/livros/salvar")
    public ResponseEntity<LivroResponseDTO> salvar(@org.springframework.web.bind.annotation.RequestBody UrlRequestDTO request) {
        Livro livroSalvo = scrapingService.salvarLivroRaspado(request.getUrl());
        LivroResponseDTO responseDTO = DtoMapper.toLivroDTO(livroSalvo);
        return ResponseEntity.ok(responseDTO);
    }
}
