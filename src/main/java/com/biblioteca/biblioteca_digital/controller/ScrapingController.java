package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.mapper.DtoMapper;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroScrapingDTO;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.service.LivroScrapingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Scraping", description = "Extração de dados de livros a partir de páginas externas")
@RestController
@RequestMapping("/api/scraping")
public class ScrapingController {

    private final LivroScrapingService scrapingService;

    public ScrapingController(LivroScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @Operation(
            summary = "Extrair informações de um livro a partir de uma URL",
            description = "Retorna os dados extraídos da página, como título, autor, preço, ISBN, categoria e ano de publicação.",
            parameters = {
                    @Parameter(name = "url", description = "URL da página do livro (ex: Amazon)", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados extraídos com sucesso",
                            content = @Content(schema = @Schema(implementation = LivroScrapingDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro ao extrair dados")
            }
    )
    @GetMapping("/livros")
    public ResponseEntity<LivroScrapingDTO> extrair(@RequestParam String url) {
        LivroScrapingDTO dto = scrapingService.extrairDadosLivro(url);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Extrair e salvar livro a partir de uma URL",
            description = "Salva no sistema o livro extraído da página, evitando duplicidade por ISBN.",
            parameters = {
                    @Parameter(name = "url", description = "URL da página do livro (ex: Amazon)", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Livro salvo com sucesso",
                            content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro ao salvar livro")
            }
    )
    @PostMapping("/livros/salvar")
    public ResponseEntity<LivroResponseDTO> salvar(@RequestParam String url) {
        Livro livroSalvo = scrapingService.salvarLivroRaspado(url);
        LivroResponseDTO responseDTO = DtoMapper.toLivroDTO(livroSalvo);
        return ResponseEntity.ok(responseDTO);
    }
}
