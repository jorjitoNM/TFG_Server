package org.server.dao.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.server.dao.model.note.Note;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_likes_notes")
public class UserLikedNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    private Note note;

    public UserLikedNote(User user, Note note) {
        this.user = user;
        this.note = note;
    }
}
