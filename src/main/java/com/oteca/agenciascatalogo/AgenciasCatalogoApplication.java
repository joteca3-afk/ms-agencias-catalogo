package com.oteca.agenciascatalogo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "API Microservicio 3: Agencias y Catálogo - Huila Travel Expedition",
                version = "v1.0",
                description = "Gestión unificada de Agencias Turísticas del Huila (RF1, RF3) y Catálogo de Categorías y Planes Turísticos (RF4, RF6, RF7, RF17).",
                contact = @Contact(name = "Juan Diego Oteca Pedreros", email = "joteca3@gmail.com")
        )
)
public class AgenciasCatalogoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgenciasCatalogoApplication.class, args);
    }
}
