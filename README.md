# 📚 Biblioteca Digital - API REST

Projeto desenvolvido como parte de um case técnico para a vaga de Desenvolvedor Backend Júnior. Esta API gerencia autores, categorias e livros, com suporte a importação via scraping de páginas da Amazon Brasil.

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
- Docker + Docker Compose

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
├── validation        # Validações personalizadas
└── BibliotecaDigitalApplication.java
```

---

## 🔍 Funcionalidades

- CRUD completo para:
    - Autores
    - Categorias
      ️- Livros
- Filtros por título, autor, ano ou categoria
- Scraping de livros da Amazon:
    - Extração de título, ISBN, preço e ano de publicação
    - Verificação se o livro já existe pelo ISBN
    - Criação automática de autor e categoria, se necessário
    - Headers personalizados e tratamento de erros
    - Registro de tentativas via log

---

## 🧪 Testes Unitários

- Cobertura básica com JUnit + Mockito para os principais serviços:
    - `AutorService`
    - `CategoriaService`
    - `LivroService`
    - `LivroScrapingService`

---

## 📄 Documentação Swagger

Disponível em:

```
http://localhost:8080/swagger-ui.html
```

Certifique-se de que a aplicação esteja em execução.

---

## 🧪 Exemplos de Requisições (Postman)

Coleção incluída: `postman-collection.json`

Inclui exemplos de:

- CRUD de autores, categorias e livros
- Listagem de livros por autor/categoria
- Scraping de livro por URL da Amazon

Exemplo de JSON:

```json
{
  "url": "https://www.amazon.com.br/dp/8535902775",
  "autorId": 1,
  "categoriaId": 2
}
```

---

## ▶️ Executando o Projeto

### ✅ Via Docker (Recomendado)

1. Compile o projeto localmente:
   ```bash
   ./mvnw clean package -DskipTests
   ```

2. Suba o container com Docker Compose:
   ```bash
   docker-compose up --build
   ```

3. Acesse:
    - API: [http://localhost:8080/api/livros](http://localhost:8080/api/livros)
    - Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    - H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

### 💻 Via Maven local (sem Docker)

```bash
./mvnw spring-boot:run
```

---

## 📌 Observações

- O projeto utiliza banco em memória (H2), reiniciado a cada execução
- Scraping compatível com Amazon Brasil (ex: `https://www.amazon.com.br/dp/8535902775`)
- Código modular, validado com boas práticas de arquitetura REST
- Docker simplifica a execução sem dependências locais

---

## 👨‍💻 Autor

Desenvolvido por **Manoelito Holanda Castelo Matos**  
Desafio técnico para vaga de Desenvolvedor Backend Júnior

---