package org.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.server.common.Constantes;
import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserLikedNote;
import org.server.dao.repositories.mysql.NoteRepository;
import org.server.dao.repositories.mysql.UserLikesNotesRepository;
import org.server.dao.repositories.mysql.UsersRepository;
import org.server.domain.errors.NoteNotFoundException;
import org.server.domain.errors.UserNotFound;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final UsersRepository usersRepository;
    private final NoteRepository notesRepository;
    private final UserLikesNotesRepository likesNotesRepository;

    public boolean likeNote(Integer noteId, UUID userId) {
        User u = usersRepository.findById(userId).orElseThrow(() -> new UserNotFound(Constantes.USER_NOT_FOUND));
        Note n = notesRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException(Constantes.NOTE_NOT_FOUND));
        return likesNotesRepository.save(new UserLikedNote(u,n))
                .getUser().getId().equals(userId);
    }


}
