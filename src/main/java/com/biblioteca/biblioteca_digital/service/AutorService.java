package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.dto.AutorRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.AutorResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;

import java.util.List;

public interface AutorService {
    List<AutorResponseDTO> listarTodos();
    AutorResponseDTO buscarPorId(Long id);
    AutorResponseDTO criar(AutorRequestDTO dto);
    AutorResponseDTO atualizar(Long id, AutorRequestDTO dto);
    void deletar(Long id);
    List<LivroResponseDTO> listarLivrosDoAutor(Long autorId);

}

