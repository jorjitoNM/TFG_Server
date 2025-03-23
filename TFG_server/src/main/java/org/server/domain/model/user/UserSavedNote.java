package org.server.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.server.domain.model.note.Note;

@AllArgsConstructor
@NoArgsConstructor
public class UserSavedNote {
    private int id;
    private User user;
    private Note note;
}
