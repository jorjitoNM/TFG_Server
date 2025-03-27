package org.server.ui.model;

import lombok.Data;
import org.server.dao.model.note.NotePrivacy;
import org.server.dao.model.note.NoteType;

import java.time.LocalDateTime;

@Data
public class NoteDTO {
    private int id;
    private String title;
    private String content;
    private NotePrivacy privacy;
    private int rating;
    private String ownerUsername;
    private int likes;
    private LocalDateTime created;
    private double latitude;
    private double longitude;
    private NoteType type;
}

