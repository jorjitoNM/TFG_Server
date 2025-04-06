package org.server.dao.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.server.dao.model.note.Note;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_saved_notes")
public class UserSavedNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"notes", "savedNotes"})
    private User user;

    @ManyToOne()
    @JoinColumn(name = "note_id", nullable = false)
    @JsonIgnoreProperties({"owner"})
    private Note note;
}
