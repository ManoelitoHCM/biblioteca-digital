package com.biblioteca.biblioteca_digital.repository;

import com.biblioteca.biblioteca_digital.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeIgnoreCase(String nome);
}
