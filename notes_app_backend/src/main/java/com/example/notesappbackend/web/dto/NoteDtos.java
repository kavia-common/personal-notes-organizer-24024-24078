package com.example.notesappbackend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * DTOs for Note API.
 */
public class NoteDtos {

    public static class CreateNoteRequest {
        @Schema(description = "Note title", example = "Grocery list")
        @NotBlank
        @Size(max = 180)
        public String title;

        @Schema(description = "Note content", example = "- Milk\n- Eggs\n- Bread")
        @NotBlank
        public String content;
    }

    public static class UpdateNoteRequest {
        @Schema(description = "Note title", example = "Updated title")
        @Size(max = 180)
        public String title;

        @Schema(description = "Note content", example = "Updated content")
        public String content;
    }

    public static class NoteResponse {
        public Long id;
        public String title;
        public String content;
        public Instant createdAt;
        public Instant updatedAt;

        public NoteResponse(Long id, String title, String content, Instant createdAt, Instant updatedAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
}
