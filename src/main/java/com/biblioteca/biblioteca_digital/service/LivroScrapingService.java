package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.dto.LivroScrapingDTO;
import com.biblioteca.biblioteca_digital.model.entity.Livro;

public interface LivroScrapingService {
    LivroScrapingDTO extrairDadosLivro(String url);
    Livro salvarLivroRaspado(String url);
}