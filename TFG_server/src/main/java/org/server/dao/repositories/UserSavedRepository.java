package org.server.dao.repositories;

import org.server.dao.model.user.UserSavedNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSavedRepository extends JpaRepository<UserSavedNote, Integer> {
    List<UserSavedNote> findByUserUsername(String username);
}
