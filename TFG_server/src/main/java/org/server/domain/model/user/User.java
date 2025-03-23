package org.server.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.server.domain.model.note.Note;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private int code;
    private String rol;
    private List<Note> notes;
    private List<User> followers;
    private List<User> following;
}
