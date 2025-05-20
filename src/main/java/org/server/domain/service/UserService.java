package org.server.domain.service;

import lombok.AllArgsConstructor;
import org.server.dao.model.note.Note;
import org.server.dao.model.user.UserSavedNote;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserSavedRepository userSavedRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public List<Note> getSavedNotesForUser(String username) {
        return userSavedRepository.findByUserUsername(username)
                .stream()
                .map(UserSavedNote::getNote)
                .toList();
    }
}
