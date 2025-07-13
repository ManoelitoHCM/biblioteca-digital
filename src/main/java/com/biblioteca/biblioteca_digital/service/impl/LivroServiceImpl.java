package com.biblioteca.biblioteca_digital.service.impl;

import com.biblioteca.biblioteca_digital.common.exception.ResourceNotFoundException;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import com.biblioteca.biblioteca_digital.service.LivroService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;

    public LivroServiceImpl(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Override
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    @Override
    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + id));
    }

    @Override
    public Livro criar(Livro livro) {
        return livroRepository.save(livro);
    }

    @Override
    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro existente = buscarPorId(id);
        livroAtualizado.setId(existente.getId());
        return livroRepository.save(livroAtualizado);
    }

    @Override
    public void deletar(Long id) {
        Livro existente = buscarPorId(id);
        livroRepository.delete(existente);
    }

    @Override
    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    public List<Livro> buscarPorCategoria(Long categoriaId) {
        return livroRepository.findByCategoriaId(categoriaId);
    }

    @Override
    public List<Livro> buscarPorAno(Integer ano) {
        return livroRepository.findByAnoPublicacao(ano);
    }

    @Override
    public List<Livro> buscarPorAutor(Long autorId) {
        return livroRepository.findByAutorId(autorId);
    }
}

