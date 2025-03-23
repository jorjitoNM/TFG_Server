package org.server.domain.model.note;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.server.domain.model.user.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class Note {
    private int id;
    private String title;
    private String content;
    private NotePrivacy privacy;
    private int rating;
    private User owner;
    private int likes;
    private LocalDateTime created;
    private double latitude;
    private double longitude;
    private NoteType type;
}
