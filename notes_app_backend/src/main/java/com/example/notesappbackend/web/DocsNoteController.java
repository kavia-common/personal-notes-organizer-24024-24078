package com.example.notesappbackend.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Documentation helper: provides usage tips within API.
 */
@RestController
@Tag(name = "Docs", description = "Documentation tips and usage")
public class DocsNoteController {

    // PUBLIC_INTERFACE
    @GetMapping("/api/docs/usage")
    @Operation(
            summary = "Usage tips",
            description = "Use Authorization: Bearer <token> after login/register. Explore endpoints at /swagger-ui.html."
    )
    public String usage() {
        return "Use Authorization: Bearer <token> after login/register. Explore endpoints at /swagger-ui.html.";
    }
}
