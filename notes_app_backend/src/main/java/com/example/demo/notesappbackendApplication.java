package com.example.notesappbackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ocean Professional - Notes API
 * Clean, modern REST API with JWT authentication and CRUD for notes.
 */
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Notes API - Ocean Professional",
                version = "0.1.0",
                description = "Modern REST API to manage personal notes with JWT authentication.\n" +
                        "Color accents: Primary #2563EB, Secondary #F59E0B, Error #EF4444.",
                contact = @Contact(name = "Notes API", email = "support@example.com")
        ),
        servers = {
                @Server(url = "/", description = "Default")
        }
)
public class NotesAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotesAppBackendApplication.class, args);
    }
}
