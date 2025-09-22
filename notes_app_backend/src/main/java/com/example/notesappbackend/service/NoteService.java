package com.example.notesappbackend.service;

import com.example.notesappbackend.model.Note;
import com.example.notesappbackend.model.User;
import com.example.notesappbackend.repository.NoteRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Business logic for Note operations with user scoping.
 */
@Service
public class NoteService {

    private final NoteRepository notes;

    public NoteService(NoteRepository notes) {
        this.notes = notes;
    }

    // PUBLIC_INTERFACE
    public Page<Note> list(User owner, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.min(100, Math.max(1, size)), Sort.by(Sort.Direction.DESC, "updatedAt"));
        return notes.findByOwner(owner, pageable);
    }

    // PUBLIC_INTERFACE
    public Note create(User owner, String title, String content) {
        Note n = new Note();
        n.setOwner(owner);
        n.setTitle(title);
        n.setContent(content);
        return notes.save(n);
    }

    // PUBLIC_INTERFACE
    public Optional<Note> findOwned(User owner, Long id) {
        return notes.findById(id).filter(n -> n.getOwner().getId().equals(owner.getId()));
    }

    // PUBLIC_INTERFACE
    public Note update(User owner, Long id, String title, String content) {
        Note n = findOwned(owner, id).orElseThrow(() -> new IllegalArgumentException("Note not found"));
        if (title != null) n.setTitle(title);
        if (content != null) n.setContent(content);
        return notes.save(n);
    }

    // PUBLIC_INTERFACE
    public void delete(User owner, Long id) {
        Note n = findOwned(owner, id).orElseThrow(() -> new IllegalArgumentException("Note not found"));
        notes.delete(n);
    }
}
