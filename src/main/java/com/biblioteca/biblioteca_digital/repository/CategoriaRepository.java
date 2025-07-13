package com.biblioteca.biblioteca_digital.repository;

import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}
