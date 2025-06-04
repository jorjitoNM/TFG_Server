package org.server.ui.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.server.dao.model.note.NotePrivacy;
import org.server.dao.model.note.NoteType;

import java.time.LocalDateTime;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true,
        defaultImpl = NoteDTO.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventNoteDTO.class, name = "EVENT"),
})
@AllArgsConstructor
@NoArgsConstructor
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
    private boolean liked;
    private boolean saved;
}

