package com.biblioteca.biblioteca_digital.service.impl;

import com.biblioteca.biblioteca_digital.mapper.DtoMapper;
import com.biblioteca.biblioteca_digital.model.dto.CategoriaRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.CategoriaResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import com.biblioteca.biblioteca_digital.service.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final LivroRepository livroRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, LivroRepository livroRepository) {
        this.categoriaRepository = categoriaRepository;
        this.livroRepository = livroRepository;
    }

    @Override
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));
    }

    @Override
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(DtoMapper::toCategoriaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponseDTO criar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        return DtoMapper.toCategoriaDTO(categoriaRepository.save(categoria));
    }

    @Override
    public List<LivroResponseDTO> listarLivrosDaCategoria(Long categoriaId) {
        return livroRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(DtoMapper::toLivroDTO)
                .collect(Collectors.toList());
    }
}