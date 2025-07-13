package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.entity.Livro;

import java.util.List;

public interface LivroService {
    List<Livro> listarTodos();
    Livro buscarPorId(Long id);
    Livro criar(Livro livro);
    Livro atualizar(Long id, Livro livro);
    void deletar(Long id);
    List<Livro> buscarPorTitulo(String titulo);
    List<Livro> buscarPorCategoria(Long categoriaId);
    List<Livro> buscarPorAno(Integer ano);
    List<Livro> buscarPorAutor(Long autorId);
}

