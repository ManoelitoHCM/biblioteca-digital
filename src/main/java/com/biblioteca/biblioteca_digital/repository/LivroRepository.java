package com.biblioteca.biblioteca_digital.repository;

import com.biblioteca.biblioteca_digital.model.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByCategoriaId(Long categoriaId);
    List<Livro> findByAnoPublicacao(Integer ano);
    List<Livro> findByAutorId(Long autorId);
    Optional<Livro> findByIsbn(String isbn);
}
