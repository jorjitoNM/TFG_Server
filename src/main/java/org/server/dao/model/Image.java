package org.server.dao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.server.dao.model.note.Note;

@Data
@NoArgsConstructor
@Entity
@Table(name = "note_images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String url;
}
