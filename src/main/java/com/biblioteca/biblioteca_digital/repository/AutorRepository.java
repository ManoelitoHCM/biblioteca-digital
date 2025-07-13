package com.biblioteca.biblioteca_digital.repository;

import com.biblioteca.biblioteca_digital.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {}

