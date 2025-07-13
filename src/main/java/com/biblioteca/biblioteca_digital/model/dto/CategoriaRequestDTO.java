package com.biblioteca.biblioteca_digital.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequestDTO {

    @NotBlank(message = "O nome da categoria é obrigatório")
    private String nome;

    private String descricao;
}

