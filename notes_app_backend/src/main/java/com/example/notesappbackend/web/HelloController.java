package com.example.notesappbackend.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Ocean Professional: Lightweight public endpoints for root, docs, health and info.
 * These endpoints are intentionally simple and unauthenticated.
 */
@RestController
@Tag(name = "Meta", description = "Meta endpoints for root, docs, health and info")
public class HelloController {

    // PUBLIC_INTERFACE
    @GetMapping("/")
    @Operation(summary = "Welcome", description = "Returns a friendly welcome message.")
    public String hello() {
        return "Welcome to Notes API - Ocean Professional";
    }

    // PUBLIC_INTERFACE
    @GetMapping("/docs")
    @Operation(summary = "API docs", description = "Redirects to Swagger UI.")
    public RedirectView docs() {
        return new RedirectView("/swagger-ui.html");
    }

    // PUBLIC_INTERFACE
    @GetMapping("/health")
    @Operation(summary = "Health", description = "Simple health status probe.")
    public String health() {
        return "OK";
    }

    // PUBLIC_INTERFACE
    @GetMapping("/api/info")
    @Operation(summary = "Info", description = "Basic application info.")
    public String info() {
        return "Notes API (Spring Boot)";
    }
}
