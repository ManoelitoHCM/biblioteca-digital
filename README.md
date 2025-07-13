# 📚 Biblioteca Digital - API REST (Spring Boot)

Projeto desenvolvido como parte de um case técnico para a vaga de Desenvolvedor Backend Júnior. Esta API gerencia autores, categorias e livros, com suporte a importação via scraping de sites externos.

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Spring Web
- H2 Database (em memória)
- Maven
- Lombok
- Bean Validation (`@Valid`)
- (To do) JSoup ou WebClient para scraping

---

## 🧱 Estrutura de Pacotes

```
com.seu_pacote.biblioteca
├── controller
├── dto
├── model (ou entity)
├── repository
├── service
│   └── impl
├── validation
└── scraping (futuro)
```

---

## 🔧 Como Executar

1. Clone o projeto:
   ```bash
   git clone https://github.com/ManoelitoHCM/biblioteca-digital.git
   cd biblioteca-digital
   ```

2. Compile e execute:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Acesse o console H2 (opcional):
   ```
   http://localhost:8080/h2-console
   JDBC URL: jdbc:h2:mem:testdb
   ```

---

## 🔗 Endpoints Disponíveis

### 📘 Livros
- `GET /api/livros` — Listar todos, com filtros: `?categoria=&autor=&ano=`
- `GET /api/livros/{id}` — Buscar por ID
- `GET /api/livros/search?titulo=...` — Buscar por título
- `POST /api/livros` — Criar novo livro
- `PUT /api/livros/{id}` — Atualizar livro
- `DELETE /api/livros/{id}` — Deletar livro

### 👤 Autores
- `GET /api/autores` — Listar todos
- `GET /api/autores/{id}` — Buscar por ID
- `POST /api/autores` — Criar autor
- `PUT /api/autores/{id}` — Atualizar autor
- `DELETE /api/autores/{id}` — Deletar autor
- `GET /api/autores/{id}/livros` — Livros de um autor

### 🏷 Categorias
- `GET /api/categorias` — Listar todas
- `POST /api/categorias` — Criar categoria
- `GET /api/categorias/{id}/livros` — Livros por categoria

### 🔎 Scraping
- `POST /api/livros/importar` — Importar dados de um livro via URL

---

## ✅ Validações

- Título do livro: obrigatório
- ISBN: deve conter 10 ou 13 dígitos
- Preço: positivo
- Ano de publicação: não pode ser futuro
- Email do autor: formato válido

---

## 🧪 Testes

Você pode testar via:

- Postman (collection incluída em `/test`)
- `curl` ou qualquer cliente HTTP

---

## 📌 Observações

- Banco H2 em memória: os dados são perdidos ao reiniciar
- O scraping será implementado com JSoup, respeitando os headers e tratando exceções