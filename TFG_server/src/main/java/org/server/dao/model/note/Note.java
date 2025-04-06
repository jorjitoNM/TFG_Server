package org.server.dao.model.note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.server.dao.model.user.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "note_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("CLASSIC")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotePrivacy privacy;

    @Column
    private int rating;

    @ManyToOne()
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnoreProperties("notes")
    private User owner;

    @Column
    private int likes;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "note_type", insertable = false, updatable = false)
    private NoteType type;
}
