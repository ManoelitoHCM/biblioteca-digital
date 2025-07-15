# Imagem base com Java 17
FROM eclipse-temurin:17-jdk-alpine

# Diretório de trabalho
WORKDIR /app

# Copia o código-fonte e configurações
COPY . .

# Compila o projeto (sem testes)
RUN ./mvnw clean package -DskipTests

# Expõe a porta do Spring Boot
EXPOSE 8080

# Executa o .jar gerado
CMD ["java", "-jar", "target/biblioteca-digital-0.0.1-SNAPSHOT.jar"]
