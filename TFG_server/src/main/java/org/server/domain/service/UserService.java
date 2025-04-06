package org.server.domain.service;

import lombok.AllArgsConstructor;
import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserSavedNote;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public boolean addNoteToSaved(String username, Long noteId) {
        boolean alreadyExists = userSavedRepository.findByUserUsername(username).stream()
                .anyMatch(savedNote -> savedNote.getNote().getId() == (noteId));

        if (alreadyExists) {
            return false;
        }

        UserSavedNote newSavedNote = new UserSavedNote();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));

        newSavedNote.setUser(user);
        newSavedNote.setNote(note);
        userSavedRepository.save(newSavedNote);
        return true;
    }
}
