package org.server.dao.repositories;

import org.server.dao.model.user.UserSavedNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserSavedRepository extends JpaRepository<UserSavedNote, Integer> {
    @Query("select u from UserSavedNote u join fetch u.note where u.user.username = :username")
    List<UserSavedNote> findByUserUsername(String username);

    boolean existsByUserUsernameAndNoteId(String username, int noteId);
}
