package com.biblioteca.biblioteca_digital.service.impl;

import com.biblioteca.biblioteca_digital.model.dto.LivroScrapingDTO;
import com.biblioteca.biblioteca_digital.model.entity.Autor;
import com.biblioteca.biblioteca_digital.model.entity.Categoria;
import com.biblioteca.biblioteca_digital.model.entity.Livro;
import com.biblioteca.biblioteca_digital.repository.AutorRepository;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.repository.LivroRepository;
import com.biblioteca.biblioteca_digital.service.LivroScrapingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LivroScrapingServiceImpl implements LivroScrapingService {

    private static final Logger logger = LoggerFactory.getLogger(LivroScrapingServiceImpl.class);

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroScrapingServiceImpl(LivroRepository livroRepository, AutorRepository autorRepository, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public LivroScrapingDTO extrairDadosLivro(String url) {
        try {
            logger.info("Iniciando extração de dados da URL: {}", url);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .timeout(10000)
                    .get();

            String titulo = doc.select("#productTitle").text();
            String autor = Optional.ofNullable(doc.select(".author a.a-link-normal").first())
                    .map(Element::text)
                    .orElse("Autor Desconhecido");

            String preco = doc.select(".a-price .a-offscreen").stream()
                    .map(Element::text)
                    .filter(text -> text.contains("R$"))
                    .findFirst()
                    .orElseGet(() -> doc.select("span.aok-offscreen").stream()
                            .map(Element::text)
                            .filter(text -> text.contains("R$"))
                            .findFirst()
                            .orElse(""));

            String isbn = doc.select("li:has(span.a-text-bold:contains(ISBN-10)) span")
                    .last()
                    .text()
                    .replaceAll("[^\\dX]", "");

            String categoria = Optional.ofNullable(doc.select("ul.zg_hrsr li span.a-list-item a").first())
                    .map(Element::text)
                    .orElse("Outros");

            String anoTexto = Optional.ofNullable(doc.select("li span:containsOwn(Data da publicação)").parents().select("span").last().text())
                    .orElse("");
            int ano = extrairAnoPublicacao(anoTexto);

            LivroScrapingDTO dto = new LivroScrapingDTO();
            dto.setTitulo(titulo);
            dto.setAutor(autor);
            dto.setPreco(parsePreco(preco));
            dto.setIsbn(isbn);
            dto.setCategoria(categoria);
            dto.setAnoPublicacao(ano);

            logger.info("Extração concluída com sucesso para título: {}", titulo);
            return dto;

        } catch (Exception e) {
            logger.error("Erro ao extrair dados da URL: {}", url, e);
            throw new RuntimeException("Erro ao extrair dados da página: " + e.getMessage(), e);
        }
    }

    public Livro salvarLivroRaspado(String url) {
        LivroScrapingDTO scrapingDTO = extrairDadosLivro(url);

        // verifica se já existe livro com mesmo ISBN
        Optional<Livro> existentes = livroRepository.findByIsbn(scrapingDTO.getIsbn());
        if (!existentes.isEmpty()) {
            logger.info("Livro com ISBN {} já existe no sistema", scrapingDTO.getIsbn());
            return existentes.get();
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

        // buscar ou criar categoria
        Categoria categoria = categoriaRepository.findByNomeIgnoreCase(scrapingDTO.getCategoria())
                .orElseGet(() -> {
                    Categoria nova = new Categoria();
                    nova.setNome(scrapingDTO.getCategoria());
                    nova.setDescricao("Categoria importada automaticamente");
                    return categoriaRepository.save(nova);
                });

        Livro livro = new Livro();
        livro.setTitulo(scrapingDTO.getTitulo());
        livro.setIsbn(scrapingDTO.getIsbn());
        livro.setAnoPublicacao(scrapingDTO.getAnoPublicacao());
        livro.setPreco(scrapingDTO.getPreco());
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        logger.info("Livro salvo com sucesso: {}", livro.getTitulo());
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

    private int extrairAnoPublicacao(String texto) {
        try {
            Pattern padrao = Pattern.compile("\\d{4}");
            Matcher matcher = padrao.matcher(texto);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group());
            }
        } catch (Exception e) {
            logger.warn("Não foi possível extrair ano da publicação: {}", texto);
        }
        return LocalDate.now().getYear();
    }
}
