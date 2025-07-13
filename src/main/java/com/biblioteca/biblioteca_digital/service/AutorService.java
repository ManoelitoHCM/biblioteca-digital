package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Livro;

import java.util.List;

public interface AutorService {
    List<Autor> listarTodos();
    Autor buscarPorId(Long id);
    Autor criar(Autor autor);
    Autor atualizar(Long id, Autor autor);
    void deletar(Long id);
    List<Livro> listarLivrosDoAutor(Long autorId);
}

