package com.biblioteca.biblioteca_digital.model.dto;

import com.biblioteca.biblioteca_digital.validation.AnoNaoFuturo;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LivroRequestDTO {

    @NotBlank(message = "O título do livro é obrigatório")
    private String titulo;

    @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN deve conter 10 ou 13 dígitos")
    private String isbn;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "O ano de publicação é obrigatório")
    @AnoNaoFuturo
    private Integer anoPublicacao;

    @NotNull(message = "O ID do autor é obrigatório")
    private Long autorId;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Long categoriaId;
}


