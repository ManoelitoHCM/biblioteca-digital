package com.biblioteca.biblioteca_digital.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String isbn;

    private Integer anoPublicacao;

    private BigDecimal preco;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    private Categoria categoria;
}
