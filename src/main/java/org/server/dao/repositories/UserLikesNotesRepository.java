package org.server.dao.repositories;

import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserLikedNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLikesNotesRepository extends JpaRepository<UserLikedNote,Integer> {
    Optional<UserLikedNote> findUserLikedNoteByUserAndNote(User user, Note note);

    boolean existsByUserUsernameAndNoteId(String username, int noteId);
}
