package com.biblioteca.biblioteca_digital.service.impl;

import com.biblioteca.biblioteca_digital.common.exception.ResourceNotFoundException;
import com.biblioteca.biblioteca_digital.model.dto.LivroRequestDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.repository.AutorRepository;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import com.biblioteca.biblioteca_digital.service.AutorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public AutorServiceImpl(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Override
    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    @Override
    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com ID: " + id));
    }

    @Override
    public Autor criar(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    public Autor atualizar(Long id, Autor autorAtualizado) {
        Autor existente = buscarPorId(id);
        autorAtualizado.setId(existente.getId());
        return autorRepository.save(autorAtualizado);
    }

    @Override
    public void deletar(Long id) {
        Autor existente = buscarPorId(id);
        autorRepository.delete(existente);
    }

    @Override
    public List<Livro> listarLivrosDoAutor(Long autorId) {
        return livroRepository.findByAutorId(autorId);
    }
}

