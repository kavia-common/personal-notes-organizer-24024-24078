package com.example.notesappbackend.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Note entity representing a personal note owned by a user.
 */
@Entity
@Table(name = "notes", indexes = {
        @Index(name = "idx_notes_owner", columnList = "owner_id"),
        @Index(name = "idx_notes_updated", columnList = "updatedAt")
})
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 180)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false, foreignKey = @ForeignKey(name = "fk_note_owner"))
    private User owner;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setTitle(String title) {
        this.title = title;
        touch();
    }

    public void setContent(String content) {
        this.content = content;
        touch();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @PreUpdate
    public void preUpdate() {
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }
}
