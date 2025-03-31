package org.server.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;
    private String username;
    private String password;
    private String email;
    private String rol;
    private List<Note> notes;
    private List<User> followers;
    private List<User> following;
}
