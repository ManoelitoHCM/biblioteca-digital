package com.biblioteca.biblioteca_digital.mapper;

import com.biblioteca.biblioteca_digital.model.dto.AutorResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.CategoriaResponseDTO;
import com.biblioteca.biblioteca_digital.model.dto.LivroResponseDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;

public class DtoMapper {

    public static LivroResponseDTO toLivroDTO(Livro l) {
        LivroResponseDTO dto = new LivroResponseDTO();
        dto.setId(l.getId());
        dto.setTitulo(l.getTitulo());
        dto.setIsbn(l.getIsbn());
        dto.setAnoPublicacao(l.getAnoPublicacao());
        dto.setPreco(l.getPreco());
        dto.setAutorId(l.getAutor().getId());
        dto.setCategoriaId(l.getCategoria().getId());
        return dto;
    }

    public static CategoriaResponseDTO toCategoriaDTO(Categoria c) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(c.getId());
        dto.setNome(c.getNome());
        dto.setDescricao(c.getDescricao());
        return dto;
    }

    public static AutorResponseDTO toAutorDTO(Autor a) {
        AutorResponseDTO dto = new AutorResponseDTO();
        dto.setId(a.getId());
        dto.setNome(a.getNome());
        dto.setEmail(a.getEmail());
        dto.setDataNascimento(a.getDataNascimento());
        return dto;
    }
}

