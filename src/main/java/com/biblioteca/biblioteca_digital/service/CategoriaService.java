package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.dto.CategoriaRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.CategoriaResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;

import java.util.List;

public interface CategoriaService {
    Categoria buscarPorId(Long id);
    List<CategoriaResponseDTO> listarTodas();
    CategoriaResponseDTO criar(CategoriaRequestDTO dto);
    List<LivroResponseDTO> listarLivrosDaCategoria(Long categoriaId);}

