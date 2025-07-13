package com.biblioteca.biblioteca_digital.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AutorRequestDTO {

    @NotBlank(message = "O nome do autor é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;
}

