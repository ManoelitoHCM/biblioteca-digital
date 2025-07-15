# 📚 Biblioteca Digital - API REST

Projeto desenvolvido como parte de um case técnico para a vaga de Desenvolvedor Backend Júnior. Esta API gerencia autores, categorias e livros, com suporte a importação via scraping de páginas da Amazon.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Spring Web
- H2 Database (em memória)
- Lombok
- Bean Validation
- JSoup (para scraping)
- Swagger (documentação)
- JUnit + Mockito (testes)

---

## 📦 Estrutura de Pacotes

```
com.biblioteca.biblioteca_digital
├── controller        # Endpoints REST
├── service           # Regras de negócio
│   └── impl          # Implementações das interfaces
├── model
│   ├── dto           # Data Transfer Objects
│   └── entity        # Entidades JPA
├── repository        # Interfaces de acesso ao banco
├── mapper            # Conversão entre entidade e DTO
├── validation        # Validação de ano
└── BibliotecaDigitalApplication.java
```

---

## 🔍 Funcionalidades

- CRUD completo para:
    - Autores
    - Categorias
    - Livros
- Busca de livros por título, autor, ano ou categoria
- Scraping de livros da Amazon:
    - Extração de título, autor, ISBN, preço, categoria e ano
    - Validação se o livro já existe pelo ISBN
    - Criação automática de autor e categoria, se não existirem
    - Registro de tentativas de importação via log
    - Headers (User-Agent) definidos para evitar bloqueios

---

## 🧪 Testes Unitários

Testes básicos implementados para:

- `AutorService`
- `CategoriaService`
- `LivroService`
- `LivroScrapingService`

Com validações de criação, atualização, exceções e listagens.

---

## 📄 Documentação Swagger

Disponível em:

```
http://localhost:8080/swagger-ui.html
```

Certifique-se de que a aplicação esteja em execução.

---

## 🧪 Exemplos de Requisições (Postman)

Coleção completa disponível em: `postman-collection.json`

Inclui exemplos de requisições para:

- Autores: criar, listar, atualizar, deletar, listar livros por autor
- Categorias: criar, listar, atualizar, deletar, listar livros por categoria
- Livros: criar, listar, atualizar, deletar
- Scraping:
    - `POST /api/scraping/livros`: extrai informações de um livro
    - `POST /api/scraping/livros/salvar`: extrai e salva no sistema

Exemplo de JSON para scraping:

```json
{
  "url": "https://www.amazon.com.br/dp/8535902775"
}
```

---

## ▶️ Executando o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/ManoelitoHCM/biblioteca-digital.git
   cd biblioteca-digital
   ```

2. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Acesse o Swagger:
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

## 🐳 Docker

Adicione `docker-compose.yml` para orquestrar a execução local da aplicação, se desejado.

---

## 📌 Observações

- Projeto usa banco de dados em memória (H2) para fácil execução local
- Código limpo, modular e com boas práticas de arquitetura REST
- O scraping funciona exclusivamente com páginas da Amazon Brasil

---

## 👨‍💻 Autor

Desenvolvido por Manoelito Holanda Castelo Matos como parte de desafio técnico.