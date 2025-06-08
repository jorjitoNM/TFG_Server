package org.server.dao.repositories;

import org.server.dao.model.note.Note;
import org.server.dao.model.user.User;
import org.server.dao.model.user.UserLikedNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLikesNotesRepository extends JpaRepository<UserLikedNote, Integer> {

    Optional<List<UserLikedNote>> findALlByUserEmail (String username);

    Optional<UserLikedNote> findUserLikedNoteByUserAndNote(User user, Note note);

    boolean existsByUserEmailAndNoteId(String email, int noteId);

    @Modifying
    @Query("DELETE FROM UserLikedNote us WHERE us.note.id = :noteId AND us.user.email = :email")
    int deleteByNoteAndUser(@Param("noteId") int noteId, @Param("email") String email);
}
