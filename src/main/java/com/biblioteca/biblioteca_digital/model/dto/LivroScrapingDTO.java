package com.biblioteca.biblioteca_digital.model.dto;

import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LivroScrapingDTO {
    private String titulo;
    private String autor;
    private BigDecimal preco;
    private String isbn;
    private String categoria;
    private Integer anoPublicacao;
}
