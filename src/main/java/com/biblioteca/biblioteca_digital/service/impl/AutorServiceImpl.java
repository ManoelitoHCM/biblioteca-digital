package com.biblioteca.biblioteca_digital.service.impl;

import com.biblioteca.biblioteca_digital.mapper.DtoMapper;
import com.biblioteca.biblioteca_digital.model.dto.AutorRequestDTO;
import com.biblioteca.biblioteca_digital.model.dto.AutorResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.repository.AutorRepository;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import com.biblioteca.biblioteca_digital.service.AutorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public AutorServiceImpl(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Override
    public List<AutorResponseDTO> listarTodos() {
        return autorRepository.findAll()
                .stream()
                .map(DtoMapper::toAutorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AutorResponseDTO buscarPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + id));
        return DtoMapper.toAutorDTO(autor);
    }

    @Override
    public AutorResponseDTO criar(AutorRequestDTO dto) {
        Autor autor = new Autor();
        autor.setNome(dto.getNome());
        autor.setEmail(dto.getEmail());
        autor.setDataNascimento(dto.getDataNascimento());
        return DtoMapper.toAutorDTO(autorRepository.save(autor));
    }

    @Override
    public AutorResponseDTO atualizar(Long id, AutorRequestDTO dto) {
        Autor existente = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + id));

        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        existente.setDataNascimento(dto.getDataNascimento());
        return DtoMapper.toAutorDTO(autorRepository.save(existente));
    }

    @Override
    public void deletar(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + id));
        autorRepository.delete(autor);
    }

    @Override
    public List<LivroResponseDTO> listarLivrosDoAutor(Long autorId) {
        return livroRepository.findByAutorId(autorId)
                .stream()
                .map(DtoMapper::toLivroDTO)
                .collect(Collectors.toList());
    }
}

