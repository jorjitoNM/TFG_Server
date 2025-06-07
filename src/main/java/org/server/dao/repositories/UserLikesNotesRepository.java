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

    Optional<List<UserLikedNote>> findAllByUserUsername (String username);

    Optional<UserLikedNote> findUserLikedNoteByUserAndNote(User user, Note note);

    boolean existsByUserUsernameAndNoteId(String username, int noteId);

    @Modifying
    @Query("DELETE FROM UserLikedNote us WHERE us.note.id = :noteId AND us.user.username = :username")
    int deleteByNoteAndUser(@Param("noteId") int noteId, @Param("username") String username);
}
