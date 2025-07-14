package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.mapper.DtoMapper;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroScrapingDTO;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.service.LivroScrapingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "Scraping", description = "Extração de dados de livros a partir de páginas externas")
@RestController
@RequestMapping("/api/scraping")
public class ScrapingController {

    private final LivroScrapingService scrapingService;

    public ScrapingController(LivroScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @Operation(summary = "Extrair informações de um livro a partir de uma URL")
    @GetMapping("/livros")
    public ResponseEntity<LivroScrapingDTO> extrair(
            @Parameter(description = "URL da página do livro (ex: Amazon)")
            @RequestParam String url) {
        LivroScrapingDTO dto = scrapingService.extrairDadosLivro(url);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Extrair e salvar livro a partir de uma URL")
    @PostMapping("/livros/salvar")
    public ResponseEntity<LivroResponseDTO> salvar(
            @Parameter(description = "URL da página do livro (ex: Amazon)")
            @RequestParam String url) {
        Livro livroSalvo = scrapingService.salvarLivroRaspado(url);
        LivroResponseDTO responseDTO = DtoMapper.toLivroDTO(livroSalvo);
        return ResponseEntity.ok(responseDTO);
    }
}
