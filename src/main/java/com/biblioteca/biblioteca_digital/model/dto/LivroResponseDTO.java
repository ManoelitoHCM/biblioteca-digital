package com.biblioteca.biblioteca_digital.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LivroResponseDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private Integer anoPublicacao;
    private BigDecimal preco;
    private Long autorId;
    private Long categoriaId;
}

