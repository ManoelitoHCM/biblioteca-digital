package com.biblioteca.biblioteca_digital.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bibliotecaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Biblioteca Digital API")
                        .description("API REST para gerenciamento de autores, livros e categorias")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Manoelito Holanda")
                                .email("manoelito_holanda@hotmail.com")
                        )
                        .license(new License().name("MIT License"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub do Projeto")
                        .url("https://github.com/ManoelitoHCM/biblioteca-digital")
                );
    }
}
