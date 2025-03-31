package org.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserLikedNote;
import org.server.dao.repositories.NoteRepository;
import org.server.dao.repositories.UserLikesNotesRepository;
import org.server.dao.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final UsersRepository usersRepository;
    private final NoteRepository notesRepository;
    private final UserLikesNotesRepository likesNotesRepository;

    public boolean likeNote(Integer noteId, UUID userId) {
        User u = usersRepository.getReferenceById(userId);
        Note n = notesRepository.getReferenceById(noteId);
        return likesNotesRepository.save(new UserLikedNote(u,n))
                .getUser().getId().equals(userId);
    }


}
