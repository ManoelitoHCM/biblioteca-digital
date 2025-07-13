package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.dto.LivroRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Livro;

import java.util.List;

public interface LivroService {
    List<LivroResponseDTO> listarTodos();
    Livro buscarPorId(Long id);
    LivroResponseDTO criar(LivroRequestDTO dto);
    LivroResponseDTO atualizar(Long id, LivroRequestDTO dto);
    void deletar(Long id);
    List<LivroResponseDTO> buscarPorTitulo(String titulo);
    List<LivroResponseDTO> buscarPorCategoria(Long categoriaId);
    List<LivroResponseDTO> buscarPorAno(Integer ano);
    List<LivroResponseDTO> buscarPorAutor(Long autorId);
}


