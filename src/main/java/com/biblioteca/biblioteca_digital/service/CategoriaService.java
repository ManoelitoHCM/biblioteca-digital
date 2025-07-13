package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;

import java.util.List;

public interface CategoriaService {
    List<Categoria> listarTodas();
    Categoria criar(Categoria categoria);
    List<Livro> listarLivrosDaCategoria(Long categoriaId);
}

