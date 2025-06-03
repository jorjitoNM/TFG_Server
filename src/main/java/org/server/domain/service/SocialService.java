package org.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.server.common.Constantes;
import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserLikedNote;
import org.server.dao.model.user.UserSavedNote;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UserRepository;
import org.server.dao.repositories.UserSavedRepository;
import org.server.domain.errors.NoteNotFoundException;
import org.server.domain.errors.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialService {
    private final UserLikesNotesRepository likesNotesRepository;

    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final UserSavedRepository userSavedRepository;

    public boolean likeNote(Integer noteId, String email) {
        User u = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(Constantes.USER_NOT_FOUND));
        Note n = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException(Constantes.NOTE_NOT_FOUND));
        Optional<UserLikedNote> likedNote = likesNotesRepository.findUserLikedNoteByUserAndNote(u, n);
        if (likedNote.isEmpty())
            likedNote = Optional.of(likesNotesRepository.save(new UserLikedNote(u, n)));
        return likedNote.get().getUser().getUsername().equals(email);
    }

    public boolean addNoteToSaved(String email, int noteId) {
        boolean alreadyExists = userSavedRepository.findByUserEmail(email).stream()
                .anyMatch(savedNote -> savedNote.getNote().getId() == (noteId));

        if (alreadyExists) {
            return false;
        }
        UserSavedNote newSavedNote = new UserSavedNote();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Nota no encontrada"));
        newSavedNote.setUser(user);
        newSavedNote.setNote(note);
        userSavedRepository.save(newSavedNote);
        return true;
    }
}
