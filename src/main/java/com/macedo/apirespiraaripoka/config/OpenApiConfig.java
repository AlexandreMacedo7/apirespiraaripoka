package com.macedo.apirespiraaripoka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Respira Aripoka")
                        .version("v1")
                        .description("Documentação OpenAPI da API Respira Aripoka")
                        .contact(new Contact().name("Equipe Respira Aripoka").email("suporte@respira.aripoka"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }
}

