package com.biblioteca.biblioteca_digital.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AutorResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
}

