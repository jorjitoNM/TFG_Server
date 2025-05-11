package org.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.server.common.Constantes;
import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserLikedNote;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UserRepository;
import org.server.domain.errors.NoteNotFoundException;
import org.server.domain.errors.UserNotFound;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final UserLikesNotesRepository likesNotesRepository;

    public boolean likeNote(Integer noteId, UUID userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(Constantes.USER_NOT_FOUND));
        Note n = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException(Constantes.NOTE_NOT_FOUND));
        Optional<UserLikedNote> likedNote = likesNotesRepository.findUserLikedNoteByUserAndNote(u,n);
        if (likedNote.isPresent())
            likedNote = Optional.of(likesNotesRepository.save(new UserLikedNote(u,n)));
        return likedNote.get().getUser().getId().equals(userId);
    }


}
