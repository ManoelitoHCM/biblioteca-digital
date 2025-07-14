package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.model.dto.LivroScrapingDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.repository.AutorRepository;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LivroScrapingService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroScrapingService(LivroRepository livroRepository, AutorRepository autorRepository, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public LivroScrapingDTO extrairDadosLivro(String url) {
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();

            String titulo = doc.select("#productTitle").text();
            String autor = doc.select(".author a.a-link-normal").first().text();

            String preco = doc.select(".a-price .a-offscreen").stream()
                    .map(el -> el.text())
                    .filter(text -> text.contains("R$"))
                    .findFirst()
                    .orElseGet(() -> doc.select("span.aok-offscreen").stream()
                            .map(el -> el.text())
                            .filter(text -> text.contains("R$"))
                            .findFirst()
                            .orElse(""));

            LivroScrapingDTO dto = new LivroScrapingDTO();
            dto.setTitulo(titulo);
            dto.setAutor(autor);
            dto.setPreco(parsePreco(preco));

            return dto;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair dados da página: " + e.getMessage(), e);
        }
    }

    public Livro salvarLivroRaspado(String url) {
        LivroScrapingDTO scrapingDTO = extrairDadosLivro(url);

        // verifica se já existe livro com mesmo título
        List<Livro> existentes = livroRepository.findByTituloContainingIgnoreCase(scrapingDTO.getTitulo());
        if (!existentes.isEmpty()) {
            return existentes.get(0);
        }

        // buscar ou criar autor
        Autor autor = autorRepository.findByNomeIgnoreCase(scrapingDTO.getAutor())
                .orElseGet(() -> {
                    Autor novo = new Autor();
                    novo.setNome(scrapingDTO.getAutor());
                    novo.setEmail(scrapingDTO.getAutor().replaceAll("\\s+", "").toLowerCase() + "@exemplo.com");
                    novo.setDataNascimento(LocalDate.of(1970, 1, 1));
                    return autorRepository.save(novo);
                });

        // usar categoria padrão (ID = 1) ou criar genérica
        Categoria categoria = categoriaRepository.findById(1L)
                .orElseGet(() -> {
                    Categoria nova = new Categoria();
                    nova.setNome("Outros");
                    nova.setDescricao("Categoria padrão");
                    return categoriaRepository.save(nova);
                });

        Livro livro = new Livro();
        livro.setTitulo(scrapingDTO.getTitulo());
        livro.setIsbn("SCRAPED-" + System.currentTimeMillis());
        livro.setAnoPublicacao(LocalDate.now().getYear());
        livro.setPreco(scrapingDTO.getPreco());
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        return livroRepository.save(livro);
    }

    private BigDecimal parsePreco(String preco) {
        try {
            String valor = preco.replaceAll("[^\\d,]", "").replace(",", ".");
            return new BigDecimal(valor);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
