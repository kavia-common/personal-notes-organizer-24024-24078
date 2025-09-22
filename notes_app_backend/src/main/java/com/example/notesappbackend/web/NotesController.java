package com.example.notesappbackend.web;

import com.example.notesappbackend.model.Note;
import com.example.notesappbackend.model.User;
import com.example.notesappbackend.service.NoteService;
import com.example.notesappbackend.service.UserService;
import com.example.notesappbackend.web.dto.NoteDtos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Notes API: CRUD endpoints scoped to authenticated user.
 */
@RestController
@RequestMapping("/api/notes")
@Tag(name = "Notes", description = "CRUD operations for notes (Ocean Professional)")
public class NotesController {

    private final NoteService noteService;
    private final UserService userService;

    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List notes", description = "Returns a paginated list of your notes.")
    public Page<NoteDtos.NoteResponse> list(
            @AuthenticationPrincipal UserDetails principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        User owner = userService.getByUsername(principal.getUsername());
        return noteService.list(owner, page, size)
                .map(n -> new NoteDtos.NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getUpdatedAt()));
        }

    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(summary = "Create note", description = "Create a new note.")
    public ResponseEntity<NoteDtos.NoteResponse> create(
            @AuthenticationPrincipal UserDetails principal,
            @Valid @RequestBody NoteDtos.CreateNoteRequest req
    ) {
        User owner = userService.getByUsername(principal.getUsername());
        Note n = noteService.create(owner, req.title, req.content);
        return ResponseEntity.ok(new NoteDtos.NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getUpdatedAt()));
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get note", description = "Get a note by id (must be owned by you).")
    public ResponseEntity<?> get(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id
    ) {
        User owner = userService.getByUsername(principal.getUsername());
        return noteService.findOwned(owner, id)
                .<ResponseEntity<?>>map(n -> ResponseEntity.ok(new NoteDtos.NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getUpdatedAt())))
                .orElseGet(() -> ResponseEntity.status(404).body("Note not found"));
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(summary = "Update note", description = "Update title/content for your note.")
    public ResponseEntity<?> update(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id,
            @Valid @RequestBody NoteDtos.UpdateNoteRequest req
    ) {
        try {
            User owner = userService.getByUsername(principal.getUsername());
            Note n = noteService.update(owner, id, req.title, req.content);
            return ResponseEntity.ok(new NoteDtos.NoteResponse(n.getId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getUpdatedAt()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body("Note not found");
        }
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete note", description = "Delete your note by id.")
    public ResponseEntity<?> delete(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id
    ) {
        try {
            User owner = userService.getByUsername(principal.getUsername());
            noteService.delete(owner, id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body("Note not found");
        }
    }
}
