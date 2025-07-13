package com.biblioteca.biblioteca_digital.service.impl;

import com.biblioteca.biblioteca_digital.model.dto.LivroRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.mapper.DtoMapper;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.repository.AutorRepository;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import com.biblioteca.biblioteca_digital.service.LivroService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroServiceImpl(LivroRepository livroRepository, AutorRepository autorRepository, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<LivroResponseDTO> listarTodos() {
        return livroRepository.findAll()
                .stream()
                .map(DtoMapper::toLivroDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));
    }

    @Override
    public LivroResponseDTO criar(LivroRequestDTO dto) {
        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + dto.getAutorId()));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + dto.getCategoriaId()));

        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setIsbn(dto.getIsbn());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setPreco(dto.getPreco());
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        return DtoMapper.toLivroDTO(livroRepository.save(livro));
    }

    @Override
    public LivroResponseDTO atualizar(Long id, LivroRequestDTO dto) {
        Livro existente = buscarPorId(id);

        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + dto.getAutorId()));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + dto.getCategoriaId()));

        existente.setTitulo(dto.getTitulo());
        existente.setIsbn(dto.getIsbn());
        existente.setAnoPublicacao(dto.getAnoPublicacao());
        existente.setPreco(dto.getPreco());
        existente.setAutor(autor);
        existente.setCategoria(categoria);

        return DtoMapper.toLivroDTO(livroRepository.save(existente));
    }

    @Override
    public void deletar(Long id) {
        Livro existente = buscarPorId(id);
        livroRepository.delete(existente);
    }

    @Override
    public List<LivroResponseDTO> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(DtoMapper::toLivroDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LivroResponseDTO> buscarPorCategoria(Long categoriaId) {
        return livroRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(DtoMapper::toLivroDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LivroResponseDTO> buscarPorAno(Integer ano) {
        return livroRepository.findByAnoPublicacao(ano)
                .stream()
                .map(DtoMapper::toLivroDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LivroResponseDTO> buscarPorAutor(Long autorId) {
        return livroRepository.findByAutorId(autorId)
                .stream()
                .map(DtoMapper::toLivroDTO)
                .collect(Collectors.toList());
    }
}