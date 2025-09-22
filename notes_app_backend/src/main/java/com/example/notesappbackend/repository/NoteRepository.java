package com.example.notesappbackend.repository;

import com.example.notesappbackend.model.Note;
import com.example.notesappbackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Note entities.
 */
public interface NoteRepository extends JpaRepository<Note, Long> {

    // PUBLIC_INTERFACE
    Page<Note> findByOwner(User owner, Pageable pageable);
}
